package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectStillExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.vcs.port.driver.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.CloneRepositoryUseCase;
import java.io.File;
import java.util.UUID;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service("CreateProjectService")
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final GetProjectPort getProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final TaskExecutor taskExecutor;

  @Autowired
  public CreateProjectService(
      @Qualifier("CreateProjectServiceNeo4j") CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      TaskExecutor taskExecutor) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.taskExecutor = taskExecutor;
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

    taskExecutor.execute(
        () -> {
          try {
            cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
          } catch (JGitInternalException e) {
            e.printStackTrace();
          }
        });
    return createProjectPort.createProject(project);
  }
}
