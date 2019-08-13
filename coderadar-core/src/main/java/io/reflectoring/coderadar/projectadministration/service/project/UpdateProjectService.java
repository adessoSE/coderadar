package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ProcessProjectService processProjectService;
  private final GetProjectCommitsUseCase getProjectCommitsUseCase;
  private final UpdateCommitsPort updateCommitsPort;

  private final ProjectStatusPort projectStatusPort;
  private final TaskScheduler taskScheduler;

  private final Logger logger = LoggerFactory.getLogger(UpdateProjectService.class);

  @Autowired
  public UpdateProjectService(
          GetProjectPort getProjectPort,
          UpdateProjectPort updateProjectPort,
          UpdateRepositoryUseCase updateRepositoryUseCase,
          CoderadarConfigurationProperties coderadarConfigurationProperties,
          ProcessProjectService processProjectService,
          GetProjectCommitsUseCase getProjectCommitsUseCase,
          UpdateCommitsPort updateCommitsPort, ProjectStatusPort projectStatusPort, TaskScheduler taskScheduler) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.updateCommitsPort = updateCommitsPort;
      this.projectStatusPort = projectStatusPort;
      this.taskScheduler = taskScheduler;
  }

  @Override
  public void update(UpdateProjectCommand command, Long projectId)
      throws ProjectIsBeingProcessedException {
    Project project = getProjectPort.get(projectId);

    if (getProjectPort
        .findByName(command.getName())
        .stream()
        .anyMatch(p -> !p.getId().equals(projectId))) {
      throw new ProjectAlreadyExistsException(command.getName());
    }

    this.processProjectService.executeTask(
        () -> {
          project.setName(command.getName());
          project.setVcsUrl(command.getVcsUrl());
          project.setVcsUsername(command.getVcsUsername());
          project.setVcsPassword(command.getVcsPassword());
          project.setVcsOnline(command.getVcsOnline());
          project.setVcsStart(command.getStartDate());
          project.setVcsEnd(command.getEndDate());

          try {
            updateRepositoryUseCase.updateRepository(
                new File(
                        coderadarConfigurationProperties.getWorkdir()
                            + "/projects/"
                            + project.getWorkdirName())
                    .toPath());

            List<Commit> commits =
                getProjectCommitsUseCase.getCommits(
                    Paths.get(project.getWorkdirName()),
                    getProjectDateRange(project));
            updateCommitsPort.updateCommits(commits, projectId);
          } catch (UnableToUpdateRepositoryException e) {
            logger.error(String.format("Unable to update project!%s", e.getMessage()));
          }
          updateProjectPort.update(project);
          scheduleUpdateTask(projectId);
        },
        projectId);
  }



    /**
     * Schedules an update task for the project. This will do a pull on the repository and save the
     * commits in the database.
     *
     * @param id Id of the project.
     */
    private void scheduleUpdateTask(Long id) {
        Project project = getProjectPort.get(id);
        if(project.getVcsEnd() != null){
            return;
        }
        taskScheduler.scheduleAtFixedRate(
                () -> {
                    if (!projectStatusPort.isBeingProcessed(id)) {
                        try {
                            logger.info(String.format("Scanning project %s for new commits!", project.getName()));
                            updateRepositoryUseCase.updateRepository(
                                    Paths.get(
                                            coderadarConfigurationProperties.getWorkdir()
                                                    + "/projects/"
                                                    + project.getWorkdirName()));
                            updateCommitsPort.updateCommits(
                                    getProjectCommitsUseCase.getCommits(
                                            Paths.get(project.getWorkdirName()), getProjectDateRange(project)),
                                    project.getId());
                        } catch (UnableToUpdateRepositoryException e) {
                            logger.error(String.format("Unable to update the project: %s", e.getMessage()));
                        }
                    }
                },
                coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000);
    }
}
