package org.wickedsource.coderadar.projectadministration.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.VcsCoordinates;
import org.wickedsource.coderadar.projectadministration.domain.Project;
import org.wickedsource.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.project.UpdateProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.project.UpdateProjectUseCase;

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
    Project project = getProjectPort.get(command.getId());
    VcsCoordinates coordinates = new VcsCoordinates();
    coordinates.setUrl(command.getVcsUrl());
    coordinates.setOnline(command.getVcsOnline());
    coordinates.setUsername(command.getVcsUsername());
    coordinates.setPassword(command.getVcsPassword());
    coordinates.setStartDate(command.getStart());
    coordinates.setEndDate(command.getEnd());
    project.setVcsCoordinates(coordinates);
    project.setName(command.getName());
    project.setWorkdirName(command.getWorkdir());
  }
}
