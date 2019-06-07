package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;
  private final GetModulePort getModulePort;

  @Autowired
  public DeleteModuleService(DeleteModulePort deleteModulePort, GetModulePort getModulePort) {
    this.deleteModulePort = deleteModulePort;
    this.getModulePort = getModulePort;
  }

  @Override
  public void delete(Long id) throws ModuleNotFoundException {
    if (getModulePort.get(id).isPresent()) {
      deleteModulePort.delete(id);
    } else {
      throw new ModuleNotFoundException(id);
    }
  }
}
