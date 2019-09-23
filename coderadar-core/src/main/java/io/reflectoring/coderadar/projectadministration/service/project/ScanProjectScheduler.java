package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
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

  private Map<Long, ScheduledFuture<?>> tasks = new HashMap<>();

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
      scheduleUpdateTask(project);
    }
  }

  /**
   * Schedules an update task for the project. This will do a pull on the repository and save the
   * commits in the database.
   *
   * @param project the project.
   */
  void scheduleUpdateTask(Project project) {
    tasks.put(
        project.getId(),
        taskScheduler.scheduleAtFixedRate(
            () -> {
              try {
                if (!projectStatusPort.isBeingProcessed(project.getId())) {
                  try {
                    Project currentProject = getProjectPort.get(project.getId());
                    if (currentProject.getVcsEnd() != null
                        && currentProject.getVcsEnd().before(new Date())) {
                      return;
                    }
                    logger.info(
                        String.format(
                            "Scanning project %s for new commits!", currentProject.getName()));
                    if (updateRepositoryUseCase.updateRepository(
                        Paths.get(
                            coderadarConfigurationProperties.getWorkdir()
                                + "/projects/"
                                + currentProject.getWorkdirName()))) {
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
              } catch (ProjectNotFoundException e) {
                ScheduledFuture f = tasks.get(project.getId());
                if(f != null){
                  f.cancel(false);
                }
              }
            },
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000));
  }
}
