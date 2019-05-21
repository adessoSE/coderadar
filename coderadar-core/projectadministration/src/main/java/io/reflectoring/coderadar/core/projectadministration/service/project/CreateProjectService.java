package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CreateProjectService")
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  @Autowired
  public CreateProjectService(
      @Qualifier("CreateProjectServiceNeo4j") CreateProjectPort createProjectPort) {
    this.createProjectPort = createProjectPort;
  }

  @Override
  public Long createProject(CreateProjectCommand command) {
    Project project = new Project();
    project.setName(command.getName());
    project.setWorkdirName(UUID.randomUUID().toString());
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    project.setVcsStart(command.getStart());
    project.setVcsEnd(command.getEnd());
    return createProjectPort.createProject(project);
  }
}
