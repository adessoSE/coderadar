package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface DeleteProjectPort {

  /**
   * Deletes a project from the database.
   *
   * @param project The project to delete.
   */
  void delete(Project project);
}
