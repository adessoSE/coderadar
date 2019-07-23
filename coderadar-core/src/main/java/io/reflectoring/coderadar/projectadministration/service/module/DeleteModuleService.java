package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;
  private final ProjectStatusPort projectStatusPort;
  private final TaskExecutor taskExecutor;

  @Autowired
  public DeleteModuleService(
      DeleteModulePort deleteModulePort,
      ProjectStatusPort projectStatusPort,
      TaskExecutor taskExecutor) {
    this.deleteModulePort = deleteModulePort;
    this.projectStatusPort = projectStatusPort;
    this.taskExecutor = taskExecutor;
  }

  @Override
  public void delete(Long id, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException {
    if (projectStatusPort.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    } else {
      projectStatusPort.setBeingProcessed(projectId, true);
      taskExecutor.execute(
          () -> {
            deleteModulePort.delete(id, projectId);
            projectStatusPort.setBeingProcessed(projectId, false);
          });
    }
  }
}
