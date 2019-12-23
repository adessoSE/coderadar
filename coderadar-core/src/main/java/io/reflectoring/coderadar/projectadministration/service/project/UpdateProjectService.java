package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryUseCase;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ProcessProjectService processProjectService;
  private final ExtractProjectCommitsUseCase extractProjectCommitsUseCase;
  private final SaveCommitPort saveCommitPort;
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final ResetAnalysisPort resetAnalysisPort;
  private final CreateModulePort createModulePort;

  private final Logger logger = LoggerFactory.getLogger(UpdateProjectService.class);

  public UpdateProjectService(
      GetProjectPort getProjectPort,
      UpdateProjectPort updateProjectPort,
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService,
      ExtractProjectCommitsUseCase extractProjectCommitsUseCase,
      SaveCommitPort saveCommitPort,
      ListModulesOfProjectUseCase listModulesOfProjectUseCase,
      ResetAnalysisPort resetAnalysisPort,
      CreateModulePort createModulePort) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.extractProjectCommitsUseCase = extractProjectCommitsUseCase;
    this.saveCommitPort = saveCommitPort;
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
    this.resetAnalysisPort = resetAnalysisPort;
    this.createModulePort = createModulePort;
  }

  @Override
  public void update(UpdateProjectCommand command, Long projectId) {
    Project project = getProjectPort.get(projectId);

    if (getProjectPort.existsByName(command.getName())
        && !getProjectPort.get(command.getName()).getId().equals(projectId)) {
      throw new ProjectAlreadyExistsException(command.getName());
    }

    project.setName(command.getName());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    boolean urlChanged = false;

    if (!project.getVcsUrl().equals(command.getVcsUrl())) {
      project.setVcsUrl(command.getVcsUrl());
      urlChanged = true;
    }

    if (projectDatesHaveChanged(project, command) || urlChanged) {
      project.setVcsStart(command.getStartDate());
      project.setVcsEnd(command.getEndDate());

      this.processProjectService.executeTask(
          () -> {
            try {
              // Check what modules where previously in the project
              List<Module> modules = listModulesOfProjectUseCase.listModules(projectId);

              // Delete all files, commits and modules as they have to be re-created
              resetAnalysisPort.resetAnalysis(projectId);
              updateProjectPort.deleteFilesAndCommits(projectId);

              // Perform a git pull on the remote repository
              updateRepositoryUseCase.updateRepository(
                  new UpdateRepositoryCommand()
                      .setLocalDir(
                          new File(
                              coderadarConfigurationProperties.getWorkdir()
                                  + "/projects/"
                                  + project.getWorkdirName()))
                      .setPassword(project.getVcsPassword())
                      .setUsername(project.getVcsUsername())
                      .setRemoteUrl(project.getVcsUrl()));

              // Get the new commit tree
              List<Commit> commits =
                  extractProjectCommitsUseCase.getCommits(
                      Paths.get(project.getWorkdirName()), getProjectDateRange(project));

              // Save the new commit tree
              saveCommitPort.saveCommits(commits, projectId);

              // Re-create the modules
              for (Module module : modules) {
                createModulePort.createModule(module.getPath(), projectId);
              }

            } catch (UnableToUpdateRepositoryException
                | ModuleAlreadyExistsException
                | ModulePathInvalidException e) {
              logger.error("Unable to update project! {}", e.getMessage());
            }
          },
          projectId);
    }
    updateProjectPort.update(project);
    logger.info("Updated project {} with id {}", project.getName(), project.getId());
  }

  private boolean projectDatesHaveChanged(Project project, UpdateProjectCommand command) {
    boolean datesChanged = false;
    if ((project.getVcsStart() == null && command.getStartDate() != null)
        || (project.getVcsStart() != null
            && !project.getVcsStart().equals(command.getStartDate()))) {
      datesChanged = true;
    }
    if ((project.getVcsEnd() == null && command.getEndDate() != null)
        || (project.getVcsEnd() != null && !project.getVcsEnd().equals(command.getEndDate()))) {
      datesChanged = true;
    }
    return datesChanged;
  }
}
