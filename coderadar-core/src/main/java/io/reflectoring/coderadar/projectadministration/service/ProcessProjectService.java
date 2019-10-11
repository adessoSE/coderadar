package io.reflectoring.coderadar.projectadministration.service;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class ProcessProjectService {

  private final AsyncListenableTaskExecutor taskExecutor;
  private final ProjectStatusPort projectStatusPort;
  private final GetProjectPort getProjectPort;

  public ProcessProjectService(
      AsyncListenableTaskExecutor taskExecutor,
      ProjectStatusPort projectStatusPort,
      GetProjectPort getProjectPort) {
    this.taskExecutor = taskExecutor;
    this.projectStatusPort = projectStatusPort;
    this.getProjectPort = getProjectPort;
  }

  public ListenableFuture<?> executeTask(Runnable runnable, Long projectId)
      throws ProjectIsBeingProcessedException {
    if (projectStatusPort.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    } else {
      projectStatusPort.setBeingProcessed(projectId, true);
      Runnable task =
          () -> {
            try {
              runnable.run();
            } finally { // No matter what happens, reset the flag
              if (getProjectPort.existsById(
                  projectId)) { // check if the project still exists, this prevents exceptions if
                // the project was deleted.
                projectStatusPort.setBeingProcessed(projectId, false);
              }
            }
          };
      return taskExecutor.submitListenable(task);
    }
  }
}
