package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.AddCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryUseCase;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ScanProjectScheduler {

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ExtractProjectCommitsUseCase extractProjectCommitsUseCase;
  private final ListProjectsPort listProjectsPort;
  private final ProjectStatusPort projectStatusPort;
  private final TaskScheduler taskScheduler;
  private final GetProjectPort getProjectPort;
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final CreateModulePort createModulePort;
  private final AddCommitsPort addCommitsPort;
  private final DeleteModulePort deleteModulePort;
  private final TaskExecutor taskExecutor;

  private final Logger logger = LoggerFactory.getLogger(ScanProjectScheduler.class);

  private Map<Long, ScheduledFuture<?>> tasks = new HashMap<>();

  public ScanProjectScheduler(
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ExtractProjectCommitsUseCase extractProjectCommitsUseCase,
      ListProjectsPort listProjectsPort,
      ProjectStatusPort projectStatusPort,
      TaskScheduler taskScheduler,
      GetProjectPort getProjectPort,
      ListModulesOfProjectUseCase listModulesOfProjectUseCase,
      CreateModulePort createModulePort,
      AddCommitsPort addCommitsPort,
      DeleteModulePort deleteModulePort,
      TaskExecutor taskExecutor) {
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.extractProjectCommitsUseCase = extractProjectCommitsUseCase;
    this.listProjectsPort = listProjectsPort;
    this.projectStatusPort = projectStatusPort;
    this.taskScheduler = taskScheduler;
    this.getProjectPort = getProjectPort;
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
    this.createModulePort = createModulePort;
    this.addCommitsPort = addCommitsPort;
    this.deleteModulePort = deleteModulePort;
    this.taskExecutor = taskExecutor;
  }

  /** Starts the scheduleCheckTask tasks upon application start */
  @EventListener({ContextRefreshedEvent.class})
  public void onApplicationEvent() {
    for (Project project : listProjectsPort.getProjects()) {
      projectStatusPort.setBeingProcessed(project.getId(), false);
    }
    taskScheduler.scheduleAtFixedRate(
        this::scheduleCheckTask,
        coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000);
  }

  /** Starts update tasks for all projects that don't have one running already. */
  private void scheduleCheckTask() {
    for (Project project : listProjectsPort.getProjects()) {
      if (!tasks.containsKey(project.getId())) {
        scheduleUpdateTask(project.getId());
      }
    }
  }

  /**
   * Schedules an update task for the project. This will do a pull on the repository and save the
   * commits in the database.
   *
   * @param projectId the project id
   */
  private void scheduleUpdateTask(Long projectId) {
    tasks.put(
        projectId,
        taskScheduler.scheduleAtFixedRate(
            () ->
                taskExecutor.execute(
                    () -> {
                      try {
                        Project currentProject = getProjectPort.get(projectId);
                        if (!projectStatusPort.isBeingProcessed(projectId)
                            && !checkProjectDate(currentProject)) {
                          projectStatusPort.setBeingProcessed(projectId, true);
                          logger.info(
                              "Scanning project {} for new commits!", currentProject.getName());
                          checkForNewCommits(currentProject);
                          projectStatusPort.setBeingProcessed(projectId, false);
                        }
                      } catch (Exception e) {
                        projectStatusPort.setBeingProcessed(projectId, false);
                        stopUpdateTask(projectId);
                      }
                    }),
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000L));
  }

  private void stopUpdateTask(Long projectId) {
    ScheduledFuture f = tasks.get(projectId);
    if (f != null) {
      f.cancel(false);
    }
    tasks.remove(projectId);
  }

  /**
   * @param project The project to check.
   * @return True if the project should be scanned for new commits, false otherwise.
   */
  private boolean checkProjectDate(Project project) {
    return project.getVcsEnd() != null && project.getVcsEnd().before(new Date());
  }

  /**
   * @param project The project to check
   * @param localDir The project workdir
   */
  private void saveCommits(Project project, File localDir, List<Branch> updatedBranches) {
    List<Commit> commits =
        extractProjectCommitsUseCase.getCommits(localDir, getProjectDateRange(project));
    addCommitsPort.addCommits(project.getId(), commits, updatedBranches);
  }

  private void checkForNewCommits(Project project) {
    try {
      File localDir =
          new File(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName());
      List<Branch> updatedBranches =
          updateRepositoryUseCase.updateRepository(
              new UpdateRepositoryCommand()
                  .setLocalDir(localDir)
                  .setPassword(project.getVcsPassword())
                  .setUsername(project.getVcsUsername())
                  .setRemoteUrl(project.getVcsUrl()));
      if (!updatedBranches.isEmpty()) {
        projectStatusPort.setBeingProcessed(project.getId(), true);

        try {
          // Check what modules where previously in the project
          List<Module> modules = listModulesOfProjectUseCase.listModules(project.getId());

          for (Module module : modules) {
            deleteModulePort.delete(module.getId(), project.getId());
          }

          saveCommits(project, localDir, updatedBranches);

          // Re-create the modules
          for (Module module : modules) {
            createModulePort.createModule(module.getPath(), project.getId());
          }
        } finally {
          projectStatusPort.setBeingProcessed(project.getId(), false);
        }
      }
    } catch (UnableToUpdateRepositoryException
        | ModuleAlreadyExistsException
        | ModulePathInvalidException e) {
      logger.error("Unable to update the project: {}", e.getMessage());
    }
  }
}
