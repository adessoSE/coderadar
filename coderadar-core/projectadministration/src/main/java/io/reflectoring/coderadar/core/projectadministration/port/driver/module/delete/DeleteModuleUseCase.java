package io.reflectoring.coderadar.core.projectadministration.port.driver.module.delete;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;

public interface DeleteModuleUseCase {
  void delete(Long id) throws ModuleNotFoundException;
}
