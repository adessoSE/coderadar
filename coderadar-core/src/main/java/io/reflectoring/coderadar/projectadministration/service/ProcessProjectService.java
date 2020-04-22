package io.reflectoring.coderadar.projectadministration.service;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class ProcessProjectService {

  private final AsyncListenableTaskExecutor taskExecutor;
  private final ProjectStatusPort projectStatusPort;
  private final GetProjectPort getProjectPort;
  private static final Logger logger = LoggerFactory.getLogger(ProcessProjectService.class);

  public ProcessProjectService(
      AsyncListenableTaskExecutor taskExecutor,
      ProjectStatusPort projectStatusPort,
      GetProjectPort getProjectPort) {
    this.taskExecutor = taskExecutor;
    this.projectStatusPort = projectStatusPort;
    this.getProjectPort = getProjectPort;
  }

  /**
   * Executes a task for a given project. The project is locked while this operation is performed
   * and cannot be modified/deleted.
   *
   * @param runnable The task to execute
   * @param projectId The id of the project.
   */
  public void executeTask(Runnable runnable, long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (projectStatusPort.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    } else {
      projectStatusPort.setBeingProcessed(projectId, true);
      Runnable task =
          () -> {
            try {
              runnable.run();
            } catch (Exception e) {
              logger.error(String.format("Project ID:%d, %s", projectId, e.getMessage()));
            } finally { // No matter what happens, reset the flag
              projectStatusPort.setBeingProcessed(projectId, false);
            }
          };
      taskExecutor.submitListenable(task);
    }
  }
}
