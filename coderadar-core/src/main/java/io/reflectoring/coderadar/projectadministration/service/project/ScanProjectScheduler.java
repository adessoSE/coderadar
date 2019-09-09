package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ScanProjectScheduler {

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final GetProjectCommitsUseCase getProjectCommitsUseCase;
  private final UpdateCommitsPort updateCommitsPort;
  private final ListProjectsPort listProjectsPort;

  private final ProjectStatusPort projectStatusPort;
  private final TaskScheduler taskScheduler;
  private final GetProjectPort getProjectPort;

  private final Logger logger = LoggerFactory.getLogger(UpdateProjectService.class);

  @Autowired
  public ScanProjectScheduler(
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      UpdateCommitsPort updateCommitsPort,
      ListProjectsPort listProjectsPort,
      ProjectStatusPort projectStatusPort,
      TaskScheduler taskScheduler,
      GetProjectPort getProjectPort) {
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.updateCommitsPort = updateCommitsPort;
    this.listProjectsPort = listProjectsPort;
    this.projectStatusPort = projectStatusPort;
    this.taskScheduler = taskScheduler;
    this.getProjectPort = getProjectPort;
  }

  @EventListener({ContextRefreshedEvent.class})
  public void onApplicationEvent() {
    for (Project project : listProjectsPort.getProjects()) {

      // TODO: Figure out how to solve the problem of a project being concurrently modified
      // scheduleUpdateTask(project);
    }
  }

  /**
   * Schedules an update task for the project. This will do a pull on the repository and save the
   * commits in the database.
   *
   * @param project the project.
   */
  void scheduleUpdateTask(Project project) {
    taskScheduler.scheduleAtFixedRate(
        () -> {
          if (!projectStatusPort.isBeingProcessed(project.getId())) {
            try {
              Project currentProject = getProjectPort.get(project.getId());
              if (currentProject.getVcsEnd() != null) {
                return;
              }

              logger.info(
                  String.format("Scanning project %s for new commits!", currentProject.getName()));
              if (updateRepositoryUseCase.updateRepository(
                  Paths.get(
                      coderadarConfigurationProperties.getWorkdir()
                          + "/projects/"
                          + currentProject.getWorkdirName()))) {
                logger.info("UPDATING COMMITS!");
                updateCommitsPort.updateCommits(
                    getProjectCommitsUseCase.getCommits(
                        Paths.get(currentProject.getWorkdirName()),
                        getProjectDateRange(currentProject)),
                    currentProject.getId());
              }
            } catch (UnableToUpdateRepositoryException e) {
              logger.error(String.format("Unable to update the project: %s", e.getMessage()));
            }
          }
        },
        coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000);
  }
}
