package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.port.driven.module.DeleteModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("DeleteModuleService")
public class DeleteModuleService implements DeleteModuleUseCase {

  private final DeleteModulePort deleteModulePort;

  @Autowired
  public DeleteModuleService(@Qualifier("DeleteModuleServiceNeo4j") DeleteModulePort deleteModulePort) {
    this.deleteModulePort = deleteModulePort;
  }

  @Override
  public void delete(Long id) {
    deleteModulePort.delete(id);
  }
}
