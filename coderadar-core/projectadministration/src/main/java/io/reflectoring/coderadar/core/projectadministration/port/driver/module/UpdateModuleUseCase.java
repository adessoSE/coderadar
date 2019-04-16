package io.reflectoring.coderadar.core.projectadministration.port.driver.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;

public interface UpdateModuleUseCase {
  Module updateModule(UpdateModuleCommand command);
}
