package io.reflectoring.coderadar.core.projectadministration.port.driven.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;

public interface DeleteModulePort {
  void delete(Long id);

  void delete(Module module);
}
