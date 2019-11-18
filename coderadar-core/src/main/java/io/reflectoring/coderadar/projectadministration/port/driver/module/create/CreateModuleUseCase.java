package io.reflectoring.coderadar.projectadministration.port.driver.module.create;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;

public interface CreateModuleUseCase {

  /**
   * Creates a module in a project.
   *
   * @param command Command containing the path of the module.
   * @param projectId The id of the project.
   * @return The id of the module.
   * @throws ModuleAlreadyExistsException Thrown if a module with the given path already exists.
   * @throws ModulePathInvalidException Thrown if the supplied path is invalid.
   */
  Long createModule(CreateModuleCommand command, Long projectId)
      throws ModulePathInvalidException, ModuleAlreadyExistsException;
}
