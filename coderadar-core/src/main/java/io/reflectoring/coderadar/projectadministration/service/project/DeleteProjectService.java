package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

  private final DeleteProjectPort deleteProjectPort;
  private final GetProjectPort getProjectPort;

  @Autowired
  public DeleteProjectService(DeleteProjectPort deleteProjectPort, GetProjectPort getProjectPort) {
    this.deleteProjectPort = deleteProjectPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public void delete(Long id) throws ProjectNotFoundException {
    if (getProjectPort.get(id).isPresent()) {
      deleteProjectPort.delete(id);
    } else {
      throw new ProjectNotFoundException(id);
    }
  }
}
