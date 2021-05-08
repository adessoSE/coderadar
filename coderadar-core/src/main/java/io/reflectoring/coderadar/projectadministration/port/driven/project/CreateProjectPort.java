package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.domain.Project;

public interface CreateProjectPort {

  /**
   * Saves a project in the DB.
   *
   * @param project The project to save.
   * @return The DB id of the project.
   */
  long createProject(Project project);
}
