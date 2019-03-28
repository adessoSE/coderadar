package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Module;
import org.wickedsource.coderadar.projectadministration.port.driven.module.GetModulePort;
import org.wickedsource.coderadar.projectadministration.port.driven.module.UpdateModulePort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.UpdateModuleCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.module.UpdateModuleUseCase;

@Service
public class UpdateModuleService implements UpdateModuleUseCase {

  private final GetModulePort getModulePort;
  private final UpdateModulePort updateModulePort;

  @Autowired
  public UpdateModuleService(GetModulePort getModulePort, UpdateModulePort updateModulePort) {
    this.getModulePort = getModulePort;
    this.updateModulePort = updateModulePort;
  }

  @Override
  public Module updateModule(UpdateModuleCommand command) {
    Module module = getModulePort.get(command.getId());
    module.setPath(command.getPath());
    module = updateModulePort.updateModule(module);
    return module;
  }
}
