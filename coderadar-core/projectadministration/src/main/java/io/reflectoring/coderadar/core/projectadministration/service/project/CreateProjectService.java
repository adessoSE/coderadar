package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.core.projectadministration.ProjectStillExistsException;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.core.vcs.port.driver.CloneRepositoryCommand;
import io.reflectoring.coderadar.core.vcs.port.driver.CloneRepositoryUseCase;
import java.io.File;
import java.util.UUID;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CreateProjectService")
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final GetProjectPort getProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public CreateProjectService(
      @Qualifier("CreateProjectServiceNeo4j") CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public Long createProject(CreateProjectCommand command) {
    if (getProjectPort.get(command.getName()).isPresent()) {
      throw new ProjectStillExistsException(command.getName());
    }
    Project project = new Project();
    project.setName(command.getName());
    project.setWorkdirName(UUID.randomUUID().toString());
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    project.setVcsStart(command.getStartDate());
    project.setVcsEnd(command.getEndDate());

    CloneRepositoryCommand cloneRepositoryCommand =
        new CloneRepositoryCommand(
            command.getVcsUrl(),
            new File(
                coderadarConfigurationProperties.getWorkdir() + "/" + project.getWorkdirName()));

    new Thread(
            () -> {
              try {
                cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
              } catch (JGitInternalException e) {
                e.printStackTrace();
              }
            })
        .start();

    return createProjectPort.createProject(project);
  }
}
