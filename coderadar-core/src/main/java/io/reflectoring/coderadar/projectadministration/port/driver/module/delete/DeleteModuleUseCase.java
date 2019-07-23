package io.reflectoring.coderadar.projectadministration.port.driver.module.delete;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;

public interface DeleteModuleUseCase {
  void delete(Long id, Long projectId)
      throws ModuleNotFoundException, ProjectIsBeingProcessedException;
}
