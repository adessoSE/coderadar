package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public void updateModule(UpdateModuleCommand command, Long moduleId)
      throws ModuleNotFoundException {
    Module module = getModulePort.get(moduleId);
    module.setPath(command.getPath());
    updateModulePort.updateModule(module);
  }
}
