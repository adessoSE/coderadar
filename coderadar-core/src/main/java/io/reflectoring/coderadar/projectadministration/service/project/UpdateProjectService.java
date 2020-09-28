package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driven.DetachContributorPort;
import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import io.reflectoring.coderadar.vcs.port.driven.GetAvailableBranchesPort;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateLocalRepositoryUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;
  private final UpdateLocalRepositoryUseCase updateLocalRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ProcessProjectService processProjectService;
  private final ExtractProjectCommitsUseCase extractProjectCommitsUseCase;
  private final SaveCommitPort saveCommitPort;
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final ResetAnalysisPort resetAnalysisPort;
  private final CreateModulePort createModulePort;
  private final GetAvailableBranchesPort getAvailableBranchesPort;
  private final DeleteProjectPort deleteProjectPort;
  private final ComputeContributorsPort computeContributorsPort;
  private final SaveContributorsPort saveContributorsPort;
  private final DetachContributorPort detachContributorPort;
  private final ListContributorsPort listContributorsPort;

  private static final Logger logger = LoggerFactory.getLogger(UpdateProjectService.class);

  @Override
  public void update(UpdateProjectCommand command, long projectId) {
    Project project = getProjectPort.get(projectId);

    if (getProjectPort.existsByName(command.getName())
        && getProjectPort.get(command.getName()).getId() != projectId) {
      throw new ProjectAlreadyExistsException(command.getName());
    }

    project.setName(command.getName());
    project.setVcsUsername(command.getVcsUsername());
    if (command.getVcsPassword() != null && !command.getVcsPassword().isEmpty()) {
      project.setVcsPassword(PasswordUtil.encrypt(command.getVcsPassword()));
    }
    boolean urlChanged = false;

    if (!project.getVcsUrl().equals(command.getVcsUrl())) {
      project.setVcsUrl(command.getVcsUrl());
      urlChanged = true;
    }

    if (projectDateHasChanged(project, command) || urlChanged) {
      project.setVcsStart(command.getStartDate());

      this.processProjectService.executeTask(
          () -> {
            try {
              // Check what modules where previously in the project
              List<Module> modules = listModulesOfProjectUseCase.listModules(projectId);

              // Delete all files, commits and modules as they have to be re-created
              resetAnalysisPort.resetAnalysis(projectId);
              deleteProjectPort.deleteBranchesFilesAndCommits(projectId);

              // Perform a git pull on the remote repository
              String localDir =
                  coderadarConfigurationProperties.getWorkdir()
                      + "/projects/"
                      + project.getWorkdirName();
              updateLocalRepositoryUseCase.updateRepository(
                  new UpdateRepositoryCommand()
                      .setLocalDir(localDir)
                      .setPassword(PasswordUtil.decrypt(project.getVcsPassword()))
                      .setUsername(project.getVcsUsername())
                      .setRemoteUrl(project.getVcsUrl()));

              // Get the new commit tree
              List<Commit> commits =
                  extractProjectCommitsUseCase.getCommits(localDir, getProjectDateRange(project));

              // Save the new commit tree
              saveCommitPort.saveCommits(
                  commits, getAvailableBranchesPort.getAvailableBranches(localDir), projectId);

              // Re-create the modules
              for (Module module : modules) {
                createModulePort.createModule(module.getPath(), projectId);
              }
              // Get contributors anew
              detachContributorPort.detachContributorsFromProject(
                  listContributorsPort.listAllByProjectId(projectId), projectId);
              saveContributors(project);
            } catch (Exception e) {
              logger.error("Unable to update project! {}", e.getMessage());
            }
          },
          projectId);
    }
    updateProjectPort.update(project);
    logger.info("Updated project {} with id {}", project.getName(), project.getId());
  }

  private boolean projectDateHasChanged(Project project, UpdateProjectCommand command) {
    boolean datesChanged = false;
    if ((project.getVcsStart() == null && command.getStartDate() != null)
        || (project.getVcsStart() != null
            && !project.getVcsStart().equals(command.getStartDate()))) {
      datesChanged = true;
    }
    return datesChanged;
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
}
