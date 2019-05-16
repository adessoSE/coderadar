package io.reflectoring.coderadar.core.projectadministration.port.driver.module.update;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;

public interface UpdateModuleUseCase {
  void updateModule(UpdateModuleCommand command, Long moduleId) throws ModuleNotFoundException;
}
