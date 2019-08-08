package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: FIX THIS
@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ProcessProjectService processProjectService;

  @Autowired
  public UpdateProjectService(
      GetProjectPort getProjectPort,
      UpdateProjectPort updateProjectPort,
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
  }

  @Override
  public void update(UpdateProjectCommand command, Long projectId)
      throws ProjectIsBeingProcessedException {
    Project project = getProjectPort.get(projectId);

    if (getProjectPort.existsByName(command.getName())) {
      throw new ProjectAlreadyExistsException(command.getName());
    }

    this.processProjectService.executeTask(
        () -> {
          project.setName(command.getName());
          project.setVcsUrl(command.getVcsUrl());
          project.setVcsUsername(command.getVcsUsername());
          project.setVcsPassword(command.getVcsPassword());
          project.setVcsOnline(command.getVcsOnline());

          if (!project.getVcsStart().equals(command.getStartDate())) {
            if (project.getVcsStart().before(command.getStartDate())) {}
            project.setVcsStart(command.getStartDate());
          }
          project.setVcsEnd(command.getEndDate());
          try {
            updateRepositoryUseCase.updateRepository(
                new File(
                        coderadarConfigurationProperties.getWorkdir()
                            + "/"
                            + project.getWorkdirName())
                    .toPath());
          } catch (UnableToUpdateRepositoryException ignored) {
          }
          updateProjectPort.update(project);
        },
        projectId);
  }
}
