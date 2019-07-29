package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.UnableToDeleteProjectException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

  private final DeleteProjectPort deleteProjectPort;
  private final ProjectStatusPort projectStatusPort;
  private final TaskExecutor taskExecutor;

  @Autowired
  public DeleteProjectService(
      DeleteProjectPort deleteProjectPort,
      ProjectStatusPort projectStatusPort,
      TaskExecutor taskExecutor) {
    this.deleteProjectPort = deleteProjectPort;
    this.projectStatusPort = projectStatusPort;
    this.taskExecutor = taskExecutor;
  }

  @Override
  public void delete(Long id) throws ProjectNotFoundException, ProjectIsBeingProcessedException {
    if (projectStatusPort.isBeingProcessed(id)) {
      throw new ProjectIsBeingProcessedException(id);
    } else {
      projectStatusPort.setBeingProcessed(id, true);
      taskExecutor.execute(
          () -> {
            try {
              deleteProjectPort.delete(id);
            } catch (UnableToDeleteProjectException e) {
              projectStatusPort.setBeingProcessed(id, false);
            }
          });
    }
  }
}
