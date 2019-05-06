package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFound;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.UpdateProjectUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  @Autowired
  public UpdateProjectService(GetProjectPort getProjectPort, UpdateProjectPort updateProjectPort) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
  }

  @Override
  public void update(UpdateProjectCommand command) {
    Optional<Project> project = getProjectPort.get(command.getId());

    if (project.isPresent()) {
      Project updatedProject = project.get();
      updatedProject.setName(command.getName());
      updatedProject.setWorkdirName(command.getWorkdir());
      updatedProject.setVcsUrl(command.getVcsUrl());
      updatedProject.setVcsUsername(command.getVcsUsername());
      updatedProject.setVcsPassword(command.getVcsPassword());
      updatedProject.setVcsOnline(command.getVcsOnline());
      updatedProject.setVcsStart(command.getStart());
      updatedProject.setVcsEnd(command.getEnd());
      updateProjectPort.update(updatedProject);
    } else {
      throw new ProjectNotFound();
    }
  }
}
