package io.reflectoring.coderadar.projectadministration.port.driver.module.update;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;

public interface UpdateModuleUseCase {
  void updateModule(UpdateModuleCommand command, Long moduleId) throws ModuleNotFoundException;
}
