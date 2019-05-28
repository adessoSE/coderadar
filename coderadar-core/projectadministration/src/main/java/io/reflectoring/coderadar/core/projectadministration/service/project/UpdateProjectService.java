package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.core.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("UpdateProjectService")
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public UpdateProjectService(
      @Qualifier("GetProjectServiceNeo4j") GetProjectPort getProjectPort,
      @Qualifier("UpdateProjectServiceNeo4j") UpdateProjectPort updateProjectPort,
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
