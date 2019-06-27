package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
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
  public void delete(Long id) throws ProjectNotFoundException {
    deleteProjectPort.delete(id);
  }
}
