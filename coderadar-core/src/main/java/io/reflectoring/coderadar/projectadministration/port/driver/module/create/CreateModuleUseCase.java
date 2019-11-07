package io.reflectoring.coderadar.projectadministration.port.driver.module.create;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;

public interface CreateModuleUseCase {
  Long createModule(CreateModuleCommand command, Long projectId)
      throws ModulePathInvalidException, ModuleAlreadyExistsException;
}
