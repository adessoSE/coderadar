package io.reflectoring.coderadar.projectadministration.port.driven.module;

public interface CreateModulePort {
  void createModule(Long moduleId, Long projectId);
}
