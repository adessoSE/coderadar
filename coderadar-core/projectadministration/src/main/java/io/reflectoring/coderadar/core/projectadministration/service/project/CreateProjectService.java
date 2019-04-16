package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.domain.VcsCoordinates;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.CreateProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  @Autowired
  public CreateProjectService(CreateProjectPort createProjectPort) {
    this.createProjectPort = createProjectPort;
  }

  @Override
  public Long createProject(CreateProjectCommand command) {
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
    return project.getId();
  }
}
