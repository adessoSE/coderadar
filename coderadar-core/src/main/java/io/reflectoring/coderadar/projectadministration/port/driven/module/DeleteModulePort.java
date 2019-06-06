package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface DeleteModulePort {
  void delete(Long id);

  void delete(Module module);
}
