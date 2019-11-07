package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface DeleteModulePort {
  void delete(Long id, Long projectId);

  void delete(Module module, Long projectId);
}
