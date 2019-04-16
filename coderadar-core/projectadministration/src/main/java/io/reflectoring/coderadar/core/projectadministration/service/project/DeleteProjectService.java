package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.DeleteProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

  private final DeleteProjectPort deleteProjectPort;

  @Autowired
  public DeleteProjectService(DeleteProjectPort deleteProjectPort) {
    this.deleteProjectPort = deleteProjectPort;
  }

  @Override
  public void delete(DeleteProjectCommand command) {
    deleteProjectPort.delete(command.getId());
  }
}
