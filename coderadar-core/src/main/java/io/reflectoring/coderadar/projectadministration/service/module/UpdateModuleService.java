package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.UpdateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.update.UpdateModuleUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("UpdateModuleService")
public class UpdateModuleService implements UpdateModuleUseCase {

  private final GetModulePort getModulePort;
  private final UpdateModulePort updateModulePort;

  @Autowired
  public UpdateModuleService(
      @Qualifier("GetModuleServiceNeo4j") GetModulePort getModulePort,
      @Qualifier("UpdateModuleServiceNeo4j") UpdateModulePort updateModulePort) {
    this.getModulePort = getModulePort;
    this.updateModulePort = updateModulePort;
  }

  @Override
  public void updateModule(UpdateModuleCommand command, Long moduleId)
      throws ModuleNotFoundException {
    Optional<Module> module = getModulePort.get(moduleId);
    if (module.isPresent()) {
      Module updatedModule = module.get();
      updatedModule.setPath(command.getPath());
      updateModulePort.updateModule(updatedModule);
    } else {
      throw new ModuleNotFoundException(moduleId);
    }
  }
}
