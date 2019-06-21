package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectStillExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.SaveProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final GetProjectPort getProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final TaskExecutor taskExecutor;

  private final SaveProjectCommitsUseCase saveProjectCommitsUseCase;

  @Autowired
  public CreateProjectService(
      CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      TaskExecutor taskExecutor,
      SaveProjectCommitsUseCase saveProjectCommitsUseCase) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.taskExecutor = taskExecutor;
    this.saveProjectCommitsUseCase = saveProjectCommitsUseCase;
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

    LocalDate projectStart;
    LocalDate projectEnd;

    if (project.getVcsStart() == null) {
      projectStart = LocalDate.of(1970, 1, 1);
    } else {
      projectStart = project.getVcsStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    if (project.getVcsEnd() == null) {
      projectEnd = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    } else {
      projectEnd = project.getVcsEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    DateRange dateRange = new DateRange(projectStart, projectEnd);

    CloneRepositoryCommand cloneRepositoryCommand =
        new CloneRepositoryCommand(
            command.getVcsUrl(),
            new File(
                coderadarConfigurationProperties.getWorkdir()
                    + "/projects/"
                    + project.getWorkdirName()));
    taskExecutor.execute(
        () -> {
          try {
            cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
            saveProjectCommitsUseCase.saveCommits(Paths.get(project.getWorkdirName()), dateRange);

          } catch (UnableToCloneRepositoryException e) {
            e.printStackTrace();
          }
        });
    return createProjectPort.createProject(project);
  }
}
