package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface SaveModulePort {
  Long saveModule(Module module, Long projectId)
      throws ModuleAlreadyExistsException, ModulePathInvalidException,
          ProjectIsBeingProcessedException;
}
