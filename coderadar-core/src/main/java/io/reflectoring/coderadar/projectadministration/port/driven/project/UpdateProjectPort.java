package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface UpdateProjectPort {
  /**
   * Updates an existing project.
   *
   * @param project The updated project.
   */
  void update(Project project);

  /**
   * Deletes all files and commits for the given project
   *
   * @param projectId The id of the project.
   */
  void deleteFilesAndCommits(long projectId);
}
