package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleService implements CreateModuleUseCase {

  private final CreateModulePort createModulePort;

  @Autowired
  public CreateModuleService(CreateModulePort createModulePort) {
    this.createModulePort = createModulePort;
  }

  @Override
  public Long createModule(CreateModuleCommand command, Long projectId)
      throws ProjectNotFoundException {
    Module module = new Module();
    module.setPath(command.getPath());
    return createModulePort.createModule(module, projectId);
  }
}
