package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.AddCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.DeleteBranchPort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateLocalRepositoryUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

@Component
public class ScanProjectScheduler {

  private final UpdateLocalRepositoryUseCase updateLocalRepositoryUseCase;
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
  private final DeleteBranchPort deleteBranchPort;

  private static final Logger logger = LoggerFactory.getLogger(ScanProjectScheduler.class);

  private Map<Long, ScheduledFuture<?>> tasks = new HashMap<>();

  public ScanProjectScheduler(
      UpdateLocalRepositoryUseCase updateLocalRepositoryUseCase,
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
      TaskExecutor taskExecutor,
      DeleteBranchPort deleteBranchPort) {
    this.updateLocalRepositoryUseCase = updateLocalRepositoryUseCase;
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
    this.deleteBranchPort = deleteBranchPort;
  }

  /** Starts the scheduleCheckTask tasks upon application start */
  @EventListener({ContextRefreshedEvent.class})
  public void onApplicationEvent() {
    for (Project project : listProjectsPort.getProjects()) {
      projectStatusPort.setBeingProcessed(project.getId(), false);
    }
    taskScheduler.scheduleAtFixedRate(
        this::scheduleCheckTask,
        coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000L);
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
  private void scheduleUpdateTask(long projectId) {
    tasks.put(
        projectId,
        taskScheduler.scheduleAtFixedRate(
            () ->
                taskExecutor.execute(
                    () -> {
                      Project currentProject;
                      try {
                        currentProject = getProjectPort.get(projectId);
                      } catch (ProjectNotFoundException e) {
                        stopUpdateTask(projectId);
                        return;
                      }
                      if (!projectStatusPort.isBeingProcessed(projectId)) {
                        projectStatusPort.setBeingProcessed(projectId, true);
                        logger.info(
                            "Scanning project {} for new commits!", currentProject.getName());
                        try {
                          checkForNewCommits(currentProject);
                        } finally {
                          projectStatusPort.setBeingProcessed(projectId, false);
                        }
                      }
                    }),
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000L));
  }

  private void stopUpdateTask(long projectId) {
    ScheduledFuture<?> f = tasks.get(projectId);
    if (f != null) {
      f.cancel(false);
    }
    tasks.remove(projectId);
  }

  private void checkForNewCommits(Project project) {
    try {
      String localDir =
          coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName();
      List<Branch> updatedBranches =
          updateLocalRepositoryUseCase.updateRepository(
              new UpdateRepositoryCommand()
                  .setLocalDir(localDir)
                  .setPassword(project.getVcsPassword())
                  .setUsername(project.getVcsUsername())
                  .setRemoteUrl(project.getVcsUrl()));
      if (!updatedBranches.isEmpty()) {
        for (Branch branch : updatedBranches) {
          if (branch.getCommitHash().equals("0000000000000000000000000000000000000000")) {
            deleteBranchPort.delete(project.getId(), branch);
          }
        }

        // Check what modules where previously in the project
        List<Module> modules = listModulesOfProjectUseCase.listModules(project.getId());

        // Delete modules
        for (Module module : modules) {
          deleteModulePort.delete(module.getId(), project.getId());
        }

        List<Commit> commits =
            extractProjectCommitsUseCase.getCommits(localDir, getProjectDateRange(project));
        addCommitsPort.addCommits(project.getId(), commits, updatedBranches);

        // Re-create the modules
        for (Module module : modules) {
          createModulePort.createModule(module.getPath(), project.getId());
        }
      }
    } catch (UnableToUpdateRepositoryException
        | ModuleAlreadyExistsException
        | ModulePathInvalidException e) {
      logger.error("Unable to update the project: {}", e.getMessage());
    }
  }
}
