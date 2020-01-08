package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final GetProjectPort getProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final ProcessProjectService processProjectService;

  private final ExtractProjectCommitsUseCase extractProjectCommitsUseCase;

  private final SaveCommitPort saveCommitPort;

  private final Logger logger = LoggerFactory.getLogger(CreateProjectService.class);

  public CreateProjectService(
      CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService,
      ExtractProjectCommitsUseCase extractProjectCommitsUseCase,
      SaveCommitPort saveCommitPort) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.extractProjectCommitsUseCase = extractProjectCommitsUseCase;
    this.saveCommitPort = saveCommitPort;
  }

  @Override
  public Long createProject(CreateProjectCommand command) {
    if (getProjectPort.existsByName(command.getName())) {
      throw new ProjectAlreadyExistsException(command.getName());
    }
    Project project = saveProject(command);
    processProjectService.executeTask(
        () -> {
          File localDir =
              new File(
                  coderadarConfigurationProperties.getWorkdir()
                      + "/projects/"
                      + project.getWorkdirName());
          CloneRepositoryCommand cloneRepositoryCommand =
              new CloneRepositoryCommand(
                  command.getVcsUrl(),
                  localDir,
                  project.getVcsUsername(),
                  project.getVcsPassword());
          try {
            cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
            logger.info(
                "Cloned project {} from repository {}",
                project.getName(),
                cloneRepositoryCommand.getRemoteUrl());
            List<Commit> commits =
                extractProjectCommitsUseCase.getCommits(localDir, getProjectDateRange(project));
            saveCommitPort.saveCommits(commits, project.getId());
            logger.info("Saved project {}", project.getName());
          } catch (Exception e) {
            logger.error("Unable to create project: {}", e.getCause().getMessage());
          }
        },
        project.getId());
    return project.getId();
  }

  /**
   * @param project The project to get the DateRange for.
   * @return A valid DateRange object from the project dates.
   */
  static DateRange getProjectDateRange(Project project) {
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

  /**
   * Saves the project in the database given the command object.
   *
   * @param command The project to save
   * @return The newly saved project.
   */
  private Project saveProject(CreateProjectCommand command) {
    String workdirName = UUID.randomUUID().toString();

    Project project = new Project();
    project.setName(command.getName());
    project.setWorkdirName(workdirName);
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.isVcsOnline());
    project.setVcsStart(command.getStartDate());
    project.setVcsEnd(command.getEndDate());
    Long projectId = createProjectPort.createProject(project);
    project.setId(projectId);
    return project;
  }
}
