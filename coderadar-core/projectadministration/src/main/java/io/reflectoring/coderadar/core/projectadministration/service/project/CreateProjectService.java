package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;

import java.io.File;
import java.util.UUID;

import io.reflectoring.coderadar.core.vcs.port.driver.CloneRepositoryCommand;
import io.reflectoring.coderadar.core.vcs.port.driver.CloneRepositoryUseCase;
import io.reflectoring.coderadar.core.vcs.service.CloneRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CreateProjectService")
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  @Autowired
  public CreateProjectService(
          @Qualifier("CreateProjectServiceNeo4j") CreateProjectPort createProjectPort, CloneRepositoryUseCase cloneRepositoryUseCase) {
    this.createProjectPort = createProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
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
    project.setVcsStart(command.getStartDate());
    project.setVcsEnd(command.getEndDate());

    CloneRepositoryCommand cloneRepositoryCommand = new CloneRepositoryCommand(command.getVcsUrl(), new File(project.getWorkdirName()));

    new Thread(() -> {
      cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
    }).start();

    return createProjectPort.createProject(project);
  }
}
