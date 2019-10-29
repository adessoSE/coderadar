package io.reflectoring.coderadar.projectadministration.service.project;

import static io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService.getProjectDateRange;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ScanProjectScheduler {

  private final UpdateRepositoryUseCase updateRepositoryUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final GetProjectCommitsUseCase getProjectCommitsUseCase;
  private final ListProjectsPort listProjectsPort;
  private final ProjectStatusPort projectStatusPort;
  private final TaskScheduler taskScheduler;
  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;
  private final SaveCommitPort saveCommitPort;
  private final ListModulesOfProjectUseCase listModulesOfProjectUseCase;
  private final CreateModuleUseCase createModuleUseCase;

  private final Logger logger = LoggerFactory.getLogger(ScanProjectScheduler.class);

  private Map<Long, ScheduledFuture<?>> tasks = new HashMap<>();

  @Autowired
  public ScanProjectScheduler(
      UpdateRepositoryUseCase updateRepositoryUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      GetProjectCommitsUseCase getProjectCommitsUseCase,
      ListProjectsPort listProjectsPort,
      ProjectStatusPort projectStatusPort,
      TaskScheduler taskScheduler,
      GetProjectPort getProjectPort,
      UpdateProjectPort updateProjectPort,
      SaveCommitPort saveCommitPort,
      ListModulesOfProjectUseCase listModulesOfProjectUseCase,
      CreateModuleUseCase createModuleUseCase) {
    this.updateRepositoryUseCase = updateRepositoryUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.getProjectCommitsUseCase = getProjectCommitsUseCase;
    this.listProjectsPort = listProjectsPort;
    this.projectStatusPort = projectStatusPort;
    this.taskScheduler = taskScheduler;
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.saveCommitPort = saveCommitPort;
    this.listModulesOfProjectUseCase = listModulesOfProjectUseCase;
    this.createModuleUseCase = createModuleUseCase;
  }

  @EventListener({ContextRefreshedEvent.class})
  public void onApplicationEvent() {
    for (Project project : listProjectsPort.getProjects()) {
      scheduleUpdateTask(project);
    }
  }

  /**
   * Schedules an update task for the project. This will do a pull on the repository and save the
   * commits in the database.
   *
   * @param project the project.
   */
  void scheduleUpdateTask(Project project) {
    tasks.put(
        project.getId(),
        taskScheduler.scheduleAtFixedRate(
            () -> {
              try {
                if (!getProjectPort.existsById(project.getId())) {
                  ScheduledFuture f = tasks.get(project.getId());
                  if (f != null) {
                    f.cancel(false);
                  }
                  return;
                }
                if (!projectStatusPort.isBeingProcessed(project.getId())) {
                  try {
                    Project currentProject = getProjectPort.get(project.getId());
                    if (currentProject.getVcsEnd() != null
                        && currentProject.getVcsEnd().before(new Date())) {
                      return;
                    }
                    logger.info(
                        String.format(
                            "Scanning project %s for new commits!", currentProject.getName()));

                    if (updateRepositoryUseCase.updateRepository(
                        Paths.get(
                            coderadarConfigurationProperties.getWorkdir()
                                + "/projects/"
                                + currentProject.getWorkdirName()),
                        new URL(currentProject.getVcsUrl()))) {

                      // Check what modules where previously in the project
                      List<GetModuleResponse> modules =
                          listModulesOfProjectUseCase.listModules(currentProject.getId());

                      // Delete all files, commits and modules as they have to be re-created
                      updateProjectPort.deleteProjectFilesCommitsAndMetrics(currentProject.getId());

                      // Get the new commit tree
                      List<Commit> commits =
                          getProjectCommitsUseCase.getCommits(
                              Paths.get(project.getWorkdirName()), getProjectDateRange(project));

                      // Save the new commit tree
                      saveCommitPort.saveCommits(commits, currentProject.getId());

                      // Re-create the modules
                      for (GetModuleResponse module : modules) {
                        createModuleUseCase.createModule(
                            new CreateModuleCommand(module.getPath()), currentProject.getId());
                      }
                    }
                  } catch (UnableToUpdateRepositoryException
                      | MalformedURLException
                      | ModuleAlreadyExistsException
                      | ModulePathInvalidException e) {
                    logger.error(String.format("Unable to update the project: %s", e.getMessage()));
                  }
                }
              } catch (ProjectNotFoundException e) {
                ScheduledFuture f = tasks.get(project.getId());
                if (f != null) {
                  f.cancel(false);
                }
              }
            },
            coderadarConfigurationProperties.getScanIntervalInSeconds() * 1000));
  }
}
