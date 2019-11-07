package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.AddCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.GetProjectHeadCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

@Component
public class ScanProjectScheduler {

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final GetProjectCommitsUseCase getProjectCommitsUseCase;
  private final ListProjectsPort listProjectsPort;
  private final ProjectStatusPort projectStatusPort;
  private final TaskScheduler taskScheduler;
  private final GetProjectPort getProjectPort;
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final CreateModuleUseCase createModuleUseCase;
  private final AddCommitsPort addCommitsPort;
  private final GetProjectHeadCommitPort getProjectHeadCommitPort;

  private final Logger logger = LoggerFactory.getLogger(ScanProjectScheduler.class);

  private Map<Long, ScheduledFuture<?>> tasks = new HashMap<>();

  @Autowired
  public ScanProjectScheduler(
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      ListProjectsPort listProjectsPort,
      ProjectStatusPort projectStatusPort,
      TaskScheduler taskScheduler,
      GetProjectPort getProjectPort,
      ListModulesOfProjectUseCase listModulesOfProjectUseCase,
      CreateModuleUseCase createModuleUseCase,
      AddCommitsPort addCommitsPort,
      GetProjectHeadCommitPort getProjectHeadCommitPort) {
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.listProjectsPort = listProjectsPort;
    this.projectStatusPort = projectStatusPort;
    this.taskScheduler = taskScheduler;
    this.getProjectPort = getProjectPort;
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
    this.createModuleUseCase = createModuleUseCase;
    this.addCommitsPort = addCommitsPort;
    this.getProjectHeadCommitPort = getProjectHeadCommitPort;
  }

  /** Starts the scheduleCheckTask tasks upon application start */
  @EventListener({ContextRefreshedEvent.class})
  public void onApplicationEvent() {
    taskScheduler.scheduleAtFixedRate(this::scheduleCheckTask, 6000);
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
            () -> {
              try {
                Project currentProject = getProjectPort.get(projectId);
                if (projectStatusPort.isBeingProcessed(projectId)
                    || checkProjectDate(currentProject)) {
                  return;
                }
                logger.info(
                    String.format(
                        "Scanning project %s for new commits!", currentProject.getName()));
                try {
                  if (updateRepositoryUseCase.updateRepository(
                      Paths.get(
                          coderadarConfigurationProperties.getWorkdir()
                              + "/projects/"
                              + currentProject.getWorkdirName()),
                      new URL(currentProject.getVcsUrl()))) {

                    // Check what modules where previously in the project
                    List<GetModuleResponse> modules =
                        listModulesOfProjectUseCase.listModules(currentProject.getId());

                    // Get the new commits
                    List<Commit> commits = getNewCommits(currentProject);

                    // Save the new commit tree
                    addCommitsPort.addCommits(commits, currentProject.getId());

                    // Re-create the modules
                    for (GetModuleResponse module : modules) {
                      createModuleUseCase.createModule(
                          new CreateModuleCommand(module.getPath()), currentProject.getId());
                    }
                  }
                } catch (UnableToUpdateRepositoryException
                    | MalformedURLException
                    | ModuleAlreadyExistsException
                    | ModulePathInvalidException e) {
                  logger.error(String.format("Unable to update the project: %s", e.getMessage()));
                }
              } catch (ProjectNotFoundException e) {
                ScheduledFuture f = tasks.get(projectId);
                if (f != null) {
                  f.cancel(false);
                }
                tasks.remove(projectId);
              }
            },
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000L));
  }

  /**
   * @param project The project to check.
   * @return True if the project should be scanned for new commits, false otherwise.
   */
  private boolean checkProjectDate(Project project) {
    return project.getVcsEnd() != null && project.getVcsEnd().before(new Date());
  }

  /**
   * @param project
   * @return Only the new commits from the local git repository.
   */
  private List<Commit> getNewCommits(Project project) {
    List<Commit> commits =
        getProjectCommitsUseCase.getCommits(
            Paths.get(project.getWorkdirName()), getProjectDateRange(project));

    Commit head = getProjectHeadCommitPort.getHeadCommit(project.getId());

    commits.removeIf(commit -> commit.getTimestamp().getTime() <= head.getTimestamp().getTime());
    return commits;
  }
}
