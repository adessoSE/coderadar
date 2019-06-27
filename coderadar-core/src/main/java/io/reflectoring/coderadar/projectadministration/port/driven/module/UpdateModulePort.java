package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface UpdateModulePort {
  void updateModule(Module module) throws ModuleNotFoundException;
}
