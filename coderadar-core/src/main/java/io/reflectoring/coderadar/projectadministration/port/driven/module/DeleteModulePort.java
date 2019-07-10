package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface DeleteModulePort {
  void delete(Long id, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException;

  void delete(Module module, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException;
}
