package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;

public interface CreateModulePort {
  Long createModule(String modulePath, Long projectId)
      throws ModuleAlreadyExistsException, ModulePathInvalidException;
}
