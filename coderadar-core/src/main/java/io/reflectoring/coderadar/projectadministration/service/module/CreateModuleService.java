package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.SaveModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleService implements CreateModuleUseCase {

  private final CreateModulePort createModulePort;
  private final SaveModulePort saveModulePort;
  private final ProcessProjectService processProjectService;

  @Autowired
  public CreateModuleService(
      CreateModulePort createModulePort,
      SaveModulePort saveModulePort,
      ProcessProjectService processProjectService) {
    this.createModulePort = createModulePort;
    this.saveModulePort = saveModulePort;
    this.processProjectService = processProjectService;
  }

  @Override
  public Long createModule(CreateModuleCommand command, Long projectId)
      throws ProjectNotFoundException, ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {

    Module module = new Module();
    module.setPath(command.getPath());
    Long moduleId = saveModulePort.saveModule(module, projectId);

    processProjectService.executeTask(
        () -> createModulePort.createModule(moduleId, projectId), projectId);
    return moduleId;
  }
}
