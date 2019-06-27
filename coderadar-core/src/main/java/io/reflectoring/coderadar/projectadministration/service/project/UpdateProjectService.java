package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public UpdateProjectService(
      GetProjectPort getProjectPort,
      UpdateProjectPort updateProjectPort,
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public void update(UpdateProjectCommand command, Long projectId) {
    Project project = getProjectPort.get(projectId);

    if (getProjectPort.existsByName(command.getName())) {
      throw new ProjectAlreadyExistsException(command.getName());
    }
    project.setName(command.getName());
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    project.setVcsStart(command.getStartDate());
    project.setVcsEnd(command.getEndDate());

    new Thread(
            () -> {
              try {
                updateRepositoryUseCase.updateRepository(
                    new File(
                            coderadarConfigurationProperties.getWorkdir()
                                + "/"
                                + project.getWorkdirName())
                        .toPath());
              } catch (UnableToUpdateRepositoryException e) {
                e.printStackTrace();
              }
            })
        .start();
    updateProjectPort.update(project);
  }
}
