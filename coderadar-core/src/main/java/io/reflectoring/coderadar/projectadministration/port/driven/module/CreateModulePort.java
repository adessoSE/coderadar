package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.ModulePathDoesNotExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;

public interface CreateModulePort {
  Long createModule(Module module, Long projectId) throws ModuleAlreadyExistsException, ModulePathDoesNotExistsException;
}
