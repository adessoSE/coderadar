package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface CreateModulePort {
  Long createModule(Module module, Long projectId);
}
