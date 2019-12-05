package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;

public interface GetModulePort {
  /**
   * Retrieves a single module given its id.
   *
   * @param id The id of the module.
   * @return The module with the id.
   */
  Module get(Long id);
}
