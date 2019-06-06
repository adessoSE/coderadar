package io.reflectoring.coderadar.projectadministration.port.driver.module.delete;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;

public interface DeleteModuleUseCase {
  void delete(Long id) throws ModuleNotFoundException;
}
