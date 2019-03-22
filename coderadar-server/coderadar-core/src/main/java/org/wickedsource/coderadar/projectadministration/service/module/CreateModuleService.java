package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Module;
import org.wickedsource.coderadar.projectadministration.port.driven.module.CreateModulePort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.CreateModuleCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.module.CreateModuleUseCase;

@Service
public class CreateModuleService implements CreateModuleUseCase {

  private final CreateModulePort createModulePort;

  @Autowired
  public CreateModuleService(CreateModulePort createModulePort) {
    this.createModulePort = createModulePort;
  }

  @Override
  public CreateModuleCommand createModule(CreateModuleCommand command) {
    Module module = new Module();
    module.setPath(command.getPath());
    module = createModulePort.createModule(module);
    return new CreateModuleCommand(module.getId(), module.getPath());
  }
}
