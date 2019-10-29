package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
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
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService implements CreateProjectUseCase {

  private final CreateProjectPort createProjectPort;

  private final GetProjectPort getProjectPort;

  private final CloneRepositoryUseCase cloneRepositoryUseCase;

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final ProcessProjectService processProjectService;

  private final GetProjectCommitsUseCase getProjectCommitsUseCase;

  private final SaveCommitPort saveCommitPort;

  private final ScanProjectScheduler scanProjectScheduler;

  private final WorkdirNameGenerator workdirNameGenerator;

  private final Logger logger = LoggerFactory.getLogger(CreateProjectUseCase.class);

  @Autowired
  public CreateProjectService(
      CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      SaveCommitPort saveCommitPort,
      ScanProjectScheduler scanProjectScheduler) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.saveCommitPort = saveCommitPort;
    this.scanProjectScheduler = scanProjectScheduler;

    this.workdirNameGenerator = new UUIDWorkdirNameGenerator();
  }

  public CreateProjectService(
      CreateProjectPort createProjectPort,
      GetProjectPort getProjectPort,
      CloneRepositoryUseCase cloneRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      SaveCommitPort saveCommitPort,
      ScanProjectScheduler scanProjectScheduler,
      WorkdirNameGenerator workdirNameGenerator) {
    this.createProjectPort = createProjectPort;
    this.getProjectPort = getProjectPort;
    this.cloneRepositoryUseCase = cloneRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.saveCommitPort = saveCommitPort;
    this.scanProjectScheduler = scanProjectScheduler;
    this.workdirNameGenerator = workdirNameGenerator;
  }

  @Override
  public Long createProject(CreateProjectCommand command) {
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
            scanProjectScheduler.scheduleUpdateTask(project);
            cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
            logger.info(
                String.format(
                    "Cloned project %s from repository %s",
                    project.getName(), cloneRepositoryCommand.getRemoteUrl()));
            List<Commit> commits =
                getProjectCommitsUseCase.getCommits(
                    Paths.get(project.getWorkdirName()), getProjectDateRange(project));
            saveCommitPort.saveCommits(commits, project.getId());
            logger.info(String.format("Saved project %s", project.getName()));
          } catch (Exception e) {
            logger.error(String.format("Unable to create project: %s", e.getMessage()));
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

  private Project saveProject(CreateProjectCommand command) {
    String workdirName = workdirNameGenerator.generate(command.getName());

    Project project = new Project();
    project.setName(command.getName());
    project.setWorkdirName(workdirName);
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

  /**
   * Interface used to generate a random working directory name for a newly created project.
   *
   * @see CreateProjectService
   */
  public interface WorkdirNameGenerator {

    /**
     * @param projectName The name of the project to generate the working directory name for.
     * @return A working directory name most likely not to collide with existing projects' working
     *     directories. Must be a valid directory name without any spaces or path separators.
     */
    String generate(String projectName);
  }

  /**
   * {@link WorkdirNameGenerator} implementation using {@link UUID}s to generate working directory
   * names.
   */
  public static class UUIDWorkdirNameGenerator implements WorkdirNameGenerator {

    /**
     * @param projectName The name of the project to generate the working directory name for.
     * @return The value returned {@link UUID#randomUUID()} as a string.
     */
    @Override
    public String generate(String projectName) {
      return UUID.randomUUID().toString();
    }
  }
}
