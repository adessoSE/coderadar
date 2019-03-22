package org.wickedsource.coderadar.projectadministration.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.projectadministration.domain.Project;
import org.wickedsource.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.project.CreateProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.project.CreateProjectUseCase;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  @Autowired
  public CreateProjectService(CreateProjectPort createProjectPort) {
    this.createProjectPort = createProjectPort;
  }

  @Override
  public CreateProjectCommand createProject(CreateProjectCommand command) {
    Project project = new Project();
    VcsCoordinates coordinates = new VcsCoordinates();
    coordinates.setUrl(command.getVcsUrl());
    coordinates.setUsername(command.getVcsUsername());
    coordinates.setPassword(command.getVcsPassword());
    coordinates.setOnline(command.getVcsOnline());
    coordinates.setStartDate(command.getStart());
    coordinates.setEndDate(command.getEnd());
    project.setName(command.getName());
    project.setWorkdirName(command.getWorkdir());
    project.setVcsCoordinates(coordinates);
    project = createProjectPort.createProject(project);
    return new CreateProjectCommand(
        project.getId(),
        project.getName(),
        project.getWorkdirName(),
        project.getVcsCoordinates().getUsername(),
        project.getVcsCoordinates().getPassword(),
        project.getVcsCoordinates().getUrl(),
        project.getVcsCoordinates().isOnline(),
        project.getVcsCoordinates().getStartDate(),
        project.getVcsCoordinates().getEndDate());
  }
}
