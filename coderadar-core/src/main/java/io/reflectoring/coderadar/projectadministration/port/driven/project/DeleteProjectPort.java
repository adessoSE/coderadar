package io.reflectoring.coderadar.projectadministration.port.driven.project;

public interface DeleteProjectPort {

  /**
   * Deletes a project given its id.
   *
   * @param projectId The id of the project.
   */
  void delete(Long projectId);
}
