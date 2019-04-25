package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.domain.VcsCoordinates;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectUseCase;
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
  public void update(UpdateProjectCommand command, Long projectId) {
    Project project = getProjectPort.get(projectId);
    VcsCoordinates coordinates = new VcsCoordinates();
    coordinates.setUrl(command.getVcsUrl());
    coordinates.setOnline(command.getVcsOnline());
    coordinates.setUsername(command.getVcsUsername());
    coordinates.setPassword(command.getVcsPassword());
    coordinates.setStartDate(command.getStart());
    coordinates.setEndDate(command.getEnd());
    project.setVcsCoordinates(coordinates);
    project.setName(command.getName());
    updateProjectPort.update(project);
  }
}
