package io.reflectoring.coderadar.projectadministration.port.driver.project.delete;

public interface DeleteProjectUseCase {

  /**
   * Deletes a project given its id.
   *
   * @param id The id of the project.
   */
  void delete(Long id);
}
