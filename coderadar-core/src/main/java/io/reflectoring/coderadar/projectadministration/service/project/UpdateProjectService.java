package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectStillExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import java.util.Optional;
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
    Optional<Project> project = getProjectPort.get(projectId);

    if (project.isPresent()) {
      if (getProjectPort.get(command.getName()).isPresent()) {
        throw new ProjectStillExistsException(command.getName());
      }
      Project updatedProject = project.get();
      updatedProject.setName(command.getName());
      updatedProject.setVcsUrl(command.getVcsUrl());
      updatedProject.setVcsUsername(command.getVcsUsername());
      updatedProject.setVcsPassword(command.getVcsPassword());
      updatedProject.setVcsOnline(command.getVcsOnline());
      updatedProject.setVcsStart(command.getStartDate());
      updatedProject.setVcsEnd(command.getEndDate());

      new Thread(
              () -> {
                updateRepositoryUseCase.updateRepository(
                    new File(
                            coderadarConfigurationProperties.getWorkdir()
                                + "/"
                                + updatedProject.getWorkdirName())
                        .toPath());
              })
          .start();

      updateProjectPort.update(updatedProject);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
