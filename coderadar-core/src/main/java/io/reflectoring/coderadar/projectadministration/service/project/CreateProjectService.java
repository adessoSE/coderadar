package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final GetProjectPort getProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final ProcessProjectService processProjectService;

  private final GetProjectCommitsUseCase getProjectCommitsUseCase;

  private final SaveCommitPort saveCommitPort;

  @Autowired
  public CreateProjectService(
      CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      SaveCommitPort saveCommitPort) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.saveCommitPort = saveCommitPort;
  }

  @Override
  public Long createProject(CreateProjectCommand command) throws ProjectIsBeingProcessedException {
    if (getProjectPort.existsByName(command.getName())) {
      throw new ProjectAlreadyExistsException(command.getName());
    }
    Project project = saveProject(command);
    processProjectService.executeTask(
        () -> {
          CloneRepositoryCommand cloneRepositoryCommand =
              new CloneRepositoryCommand(
                  command.getVcsUrl(),
                  new File(
                      coderadarConfigurationProperties.getWorkdir()
                          + "/projects/"
                          + project.getWorkdirName()));
          try {
            cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
            saveCommitPort.saveCommits(
                getProjectCommitsUseCase.getCommits(
                    Paths.get(project.getWorkdirName()), getProjectDateRange(project)),
                project.getId());
          } catch (UnableToCloneRepositoryException e) {
            e.printStackTrace();
          }
        },
        project.getId());
    return project.getId();
  }

  /**
   * @param project The project to get the DateRange for.
   * @return A valid DateRange object from the project dates.
   */
  private DateRange getProjectDateRange(Project project) {
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
    return new DateRange(projectStart, projectEnd);
  }

  private Project saveProject(CreateProjectCommand command) {
    Project project = new Project();
    project.setName(command.getName());
    project.setWorkdirName(UUID.randomUUID().toString());
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    project.setVcsStart(command.getStartDate());
    project.setVcsEnd(command.getEndDate());
    project.setBeingProcessed(false);
    Long projectId = createProjectPort.createProject(project);
    project.setId(projectId);
    return project;
  }
}
