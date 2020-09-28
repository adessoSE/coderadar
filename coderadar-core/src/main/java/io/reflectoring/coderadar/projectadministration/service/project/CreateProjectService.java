package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.port.driver.ListAnalyzersUseCase;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.*;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserRoleForProjectPort;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import io.reflectoring.coderadar.vcs.port.driven.GetAvailableBranchesPort;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

  private final GetAvailableBranchesPort getAvailableBranchesPort;
  private final CreateProjectPort createProjectPort;
  private final GetProjectPort getProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ProcessProjectService processProjectService;
  private final SaveCommitPort saveCommitPort;
  private final ComputeContributorsPort computeContributorsPort;
  private final SaveContributorsPort saveContributorsPort;
  private final ListContributorsPort listContributorsPort;
  private final SetUserRoleForProjectPort setUserRoleForProjectPort;
  private final GetUserPort getUserPort;
  private final CloneRepositoryUseCase cloneRepositoryUseCase;
  private final ExtractProjectCommitsUseCase extractProjectCommitsUseCase;
  private final CreateFilePatternPort createFilePatternPort;
  private final CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort;
  private final ListAnalyzersUseCase listAnalyzersUseCase;

  private static final Logger logger = LoggerFactory.getLogger(CreateProjectService.class);

  @Override
  public Long createProject(CreateProjectCommand command) {
    if (getProjectPort.existsByName(command.getName())) {
      throw new ProjectAlreadyExistsException(command.getName());
    }
    Project project = saveProject(command);
    setAdminRoleForCurrentUser(project.getId());
    processProjectService.executeTask(
        () -> {
          String localDir =
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName();
          CloneRepositoryCommand cloneRepositoryCommand =
              new CloneRepositoryCommand(
                  command.getVcsUrl(),
                  localDir,
                  project.getVcsUsername(),
                  PasswordUtil.decrypt(project.getVcsPassword()));
          try {
            cloneRepositoryUseCase.cloneRepository(cloneRepositoryCommand);
            logger.info(
                "Cloned project {} from repository {}",
                project.getName(),
                cloneRepositoryCommand.getRemoteUrl());

            saveContributors(project);
            List<Commit> commits =
                extractProjectCommitsUseCase.getCommits(localDir, getProjectDateRange(project));
            List<Branch> branches = getAvailableBranchesPort.getAvailableBranches(localDir);
            saveCommitPort.saveCommits(commits, branches, project.getId());
            logger.info("Saved project {}", project.getName());
          } catch (Exception e) {
            logger.error("Unable to create project: {}", e.getMessage());
          }
        },
        project.getId());
    setDefaultConfiguration(project.getId());
    return project.getId();
  }

  private void setDefaultConfiguration(long id) {
    // Set default file pattern
    createFilePatternPort.createFilePattern(
        new FilePattern(0, "**/*.java", InclusionType.INCLUDE), id);

    // Add all analyzers per default
    for (String analyzerName : listAnalyzersUseCase.listAvailableAnalyzers()) {
      createAnalyzerConfigurationPort.create(new AnalyzerConfiguration(0, analyzerName, true), id);
    }
  }

  private void setAdminRoleForCurrentUser(Long projectId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof String) {
      setUserRoleForProjectPort.setRole(
          projectId,
          getUserPort.getUserByUsername(((String) authentication.getPrincipal())).getId(),
          ProjectRole.ADMIN);
    }
  }

  private synchronized void saveContributors(Project project) {
    List<Contributor> contributors = listContributorsPort.listAll();
    contributors =
        computeContributorsPort.computeContributors(
            coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName(),
            contributors,
            getProjectDateRange(project));
    saveContributorsPort.save(contributors, project.getId());
  }

  /**
   * @param project The project to get the DateRange for.
   * @return A valid DateRange object from the project dates.
   */
  static DateRange getProjectDateRange(Project project) {
    LocalDate projectStart;
    if (project.getVcsStart() == null) {
      projectStart = LocalDate.of(1970, 1, 1);
    } else {
      projectStart = project.getVcsStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    return new DateRange(
        projectStart, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
  }

  /**
   * Saves the project in the database given the command object.
   *
   * @param command The project to save
   * @return The newly saved project.
   */
  private Project saveProject(CreateProjectCommand command) {
    String workdirName = String.format("%s-%s", command.getName(), UUID.randomUUID().toString());

    Project project = new Project();
    project.setName(command.getName());
    project.setWorkdirName(workdirName);
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(PasswordUtil.encrypt(command.getVcsPassword()));
    project.setVcsStart(command.getStartDate());
    long projectId = createProjectPort.createProject(project);
    project.setId(projectId);
    return project;
  }
}
