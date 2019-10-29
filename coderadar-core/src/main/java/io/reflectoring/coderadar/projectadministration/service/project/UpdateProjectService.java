package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ProcessProjectService processProjectService;
  private final GetProjectCommitsUseCase getProjectCommitsUseCase;
  private final SaveCommitPort saveCommitPort;
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final CreateModuleUseCase createModuleUseCase;

  private final Logger logger = LoggerFactory.getLogger(UpdateProjectService.class);

  @Autowired
  public UpdateProjectService(
      GetProjectPort getProjectPort,
      UpdateProjectPort updateProjectPort,
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessProjectService processProjectService,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      SaveCommitPort saveCommitPort,
      ListModulesOfProjectUseCase listModulesOfProjectUseCase,
      CreateModuleUseCase createModuleUseCase) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.processProjectService = processProjectService;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.saveCommitPort = saveCommitPort;
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
    this.createModuleUseCase = createModuleUseCase;
  }

  @Override
  public void update(UpdateProjectCommand command, Long projectId) {
    Project project = getProjectPort.get(projectId);

    if (getProjectPort
        .findByName(command.getName())
        .stream()
        .anyMatch(p -> !p.getId().equals(projectId))) {
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
              List<GetModuleResponse> modules = listModulesOfProjectUseCase.listModules(projectId);

              // Delete all files, commits and modules as they have to be re-created
              updateProjectPort.deleteProjectFilesCommitsAndMetrics(projectId);

              // Perform a git pull on the remote repository
              updateRepositoryUseCase.updateRepository(
                  new File(
                          coderadarConfigurationProperties.getWorkdir()
                              + "/projects/"
                              + project.getWorkdirName())
                      .toPath(),
                  new URL(project.getVcsUrl()));

              // Get the new commit tree
              List<Commit> commits =
                  getProjectCommitsUseCase.getCommits(
                      Paths.get(project.getWorkdirName()), getProjectDateRange(project));

              // Save the new commit tree
              saveCommitPort.saveCommits(commits, projectId);

              // Re-create the modules
              for (GetModuleResponse module : modules) {
                createModuleUseCase.createModule(
                    new CreateModuleCommand(module.getPath()), projectId);
              }

            } catch (UnableToUpdateRepositoryException
                | MalformedURLException
                | ModuleAlreadyExistsException
                | ModulePathInvalidException e) {
              logger.error(String.format("Unable to update project! %s", e.getMessage()));
            }
          },
          projectId);
    }
    updateProjectPort.update(project);
    logger.info(String.format("Updated project %s with id %d", project.getName(), project.getId()));
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
