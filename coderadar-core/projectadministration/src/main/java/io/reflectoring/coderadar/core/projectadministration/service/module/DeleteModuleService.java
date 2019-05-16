package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("DeleteModuleService")
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;
  private final GetModulePort getModulePort;

  @Autowired
  public DeleteModuleService(@Qualifier("DeleteModuleServiceNeo4j") DeleteModulePort deleteModulePort, GetModulePort getModulePort) {
    this.deleteModulePort = deleteModulePort;
    this.getModulePort = getModulePort;
  }

  @Override
  public void delete(Long id) throws ModuleNotFoundException {
    if(getModulePort.get(id).isPresent()) {
      deleteModulePort.delete(id);
    }else {
      throw new ModuleNotFoundException(id);
    }
  }
}
