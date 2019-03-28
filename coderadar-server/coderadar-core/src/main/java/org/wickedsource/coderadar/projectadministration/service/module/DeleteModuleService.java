package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Module;
import org.wickedsource.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.DeleteModuleCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.module.DeleteModuleUseCase;

@Service
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;

  @Autowired
  public DeleteModuleService(DeleteModulePort deleteModulePort) {
    this.deleteModulePort = deleteModulePort;
  }

  @Override
  public void deleteModule(DeleteModuleCommand command) {
    Module module = new Module();
    module.setId(command.getId());
    deleteModulePort.deleteModule(module);
  }
}
