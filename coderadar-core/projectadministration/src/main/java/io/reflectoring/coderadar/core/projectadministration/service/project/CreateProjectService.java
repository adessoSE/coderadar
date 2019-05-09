package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;

import io.reflectoring.coderadar.core.vcs.port.driven.CloneRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;
  private final CloneRepositoryPort cloneRepositoryPort;

  @Autowired
  public CreateProjectService(CreateProjectPort createProjectPort, CloneRepositoryPort cloneRepositoryPort) {
    this.createProjectPort = createProjectPort;
    this.cloneRepositoryPort = cloneRepositoryPort;
  }

  @Override
  public Long createProject(CreateProjectCommand command) {
    Project project = new Project();
    project.setName(command.getName());
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    project.setVcsStart(command.getStart());
    project.setVcsEnd(command.getEnd());
    cloneRepositoryPort.cloneRepository(command.getVcsUrl().toString(), new File(UUID.randomUUID().toString()));
    return createProjectPort.createProject(project);
  }
}
