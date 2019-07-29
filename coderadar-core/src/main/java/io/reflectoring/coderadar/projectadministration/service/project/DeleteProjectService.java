package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

  private final DeleteProjectPort deleteProjectPort;
  private final ProcessProjectService processProjectService;

  @Autowired
  public DeleteProjectService(
          DeleteProjectPort deleteProjectPort, ProcessProjectService processProjectService) {
    this.deleteProjectPort = deleteProjectPort;
    this.processProjectService = processProjectService;
  }

  @Override
  public void delete(Long id) throws ProjectNotFoundException, ProjectIsBeingProcessedException {
    processProjectService.executeTask(() -> deleteProjectPort.delete(id), id);
  }
}
