package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.domain.Project;

public interface UpdateProjectPort {
  /**
   * Updates an existing project.
   *
   * @param project The updated project.
   */
  void update(Project project);
}
