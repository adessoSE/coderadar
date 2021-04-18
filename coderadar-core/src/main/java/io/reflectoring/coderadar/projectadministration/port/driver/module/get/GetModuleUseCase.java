package io.reflectoring.coderadar.projectadministration.port.driver.module.get;

import io.reflectoring.coderadar.domain.Module;

public interface GetModuleUseCase {

  /**
   * Retrieves a single module given its id.
   *
   * @param id The id of the module.
   * @return The module with the id.
   */
  Module get(long id);
}
