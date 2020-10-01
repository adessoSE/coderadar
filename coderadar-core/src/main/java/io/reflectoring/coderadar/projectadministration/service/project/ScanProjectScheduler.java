package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.CoderadarConstants;
import io.reflectoring.coderadar.analyzer.service.AnalyzingService;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.DeleteBranchPort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateLocalRepositoryUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
  private final UpdateCommitsPort updateCommitsPort;
  private final DeleteModulePort deleteModulePort;
  private final AsyncListenableTaskExecutor taskExecutor;
  private final DeleteBranchPort deleteBranchPort;
  private final AnalyzingService analyzingService;

  private static final Logger logger = LoggerFactory.getLogger(ScanProjectScheduler.class);

  private final Map<Long, ScheduledFuture<?>> scheduledTasks = new HashMap<>();
  private final Map<Long, Future<?>> tasks = new HashMap<>();
  private ScheduledFuture<?> mainTask;

  /** Starts the scheduleCheckTask tasks upon application start */
  @PostConstruct
  public void onApplicationStart() {
    for (Project project : listProjectsPort.getProjects()) {
      projectStatusPort.setBeingProcessed(project.getId(), false);
    }
    mainTask =
        taskScheduler.scheduleAtFixedRate(
            this::scheduleCheckTask,
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000L);
  }

  /** Starts update tasks for all projects that don't have one running already. */
  private void scheduleCheckTask() {
    for (Project project : listProjectsPort.getProjects()) {
      if (!scheduledTasks.containsKey(project.getId())) {
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
    scheduledTasks.put(
        projectId,
        taskScheduler.scheduleAtFixedRate(
            () ->
                tasks.put(
                    projectId,
                    taskExecutor.submit(
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
                            List<String> updatedBranches = Collections.emptyList();
                            try {
                              updatedBranches = checkForNewCommits(currentProject);
                            } finally {
                              projectStatusPort.setBeingProcessed(projectId, false);
                              if (!updatedBranches.isEmpty()) {
                                analyzingService.start(projectId, updatedBranches);
                              }
                            }
                          }
                          removeTask(projectId);
                        })),
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000L));
  }

  public void stopUpdateTask(long projectId) {
    ScheduledFuture<?> f = scheduledTasks.get(projectId);
    if (f != null) {
      f.cancel(false);
    }
    scheduledTasks.remove(projectId);
    removeTask(projectId);
  }

  private void removeTask(long projectId) {
    tasks.remove(projectId);
    synchronized (tasks) {
      tasks.notifyAll();
    }
  }

  public List<String> checkForNewCommits(Project project) {
    try {
      String localDir =
          coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName();

      List<Branch> updatedBranches =
          updateLocalRepositoryUseCase.updateRepository(
              new UpdateRepositoryCommand()
                  .setLocalDir(localDir)
                  .setPassword(PasswordUtil.decrypt(project.getVcsPassword()))
                  .setUsername(project.getVcsUsername())
                  .setRemoteUrl(project.getVcsUrl()));

      if (!updatedBranches.isEmpty()) {
        for (Branch branch : updatedBranches) {
          if (branch.getCommitHash().equals(CoderadarConstants.ZERO_HASH)) {
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

        updateCommitsPort.updateCommits(project.getId(), commits, updatedBranches);

        // Re-create the modules
        for (Module module : modules) {
          createModulePort.createModule(module.getPath(), project.getId());
        }
      }
      return updatedBranches.stream().map(Branch::getName).collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Unable to update the project: {}", e.toString());
    }
    return Collections.emptyList();
  }

  public void onShutdown() throws InterruptedException {
    mainTask.cancel(true);
    for (var task : scheduledTasks.values()) {
      task.cancel(true);
    }
    while (!tasks.isEmpty()) {
      synchronized (tasks) {
        tasks.wait();
      }
    }
  }
}
