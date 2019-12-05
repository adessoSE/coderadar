package io.reflectoring.coderadar.projectadministration.port.driven.module;

public interface DeleteModulePort {

  /**
   * Deletes a module given its id.
   *
   * @param id The id of the module
   * @param projectId The id of the project
   */
  void delete(Long id, Long projectId);
}
