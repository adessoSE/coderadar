package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;
  private final ProcessProjectService processProjectService;
  private final GetModulePort getModulePort;

  @Autowired
  public DeleteModuleService(
          DeleteModulePort deleteModulePort,
          ProcessProjectService processProjectService, GetModulePort getModulePort) {
    this.deleteModulePort = deleteModulePort;
    this.processProjectService = processProjectService;
    this.getModulePort = getModulePort;
  }

  @Override
  public void delete(Long id, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException {
    getModulePort.get(id);
    processProjectService.executeTask(() -> deleteModulePort.delete(id, projectId), projectId);
  }
}
