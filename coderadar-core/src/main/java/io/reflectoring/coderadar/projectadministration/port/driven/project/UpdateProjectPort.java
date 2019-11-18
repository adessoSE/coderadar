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
   * Deletes all files, commits and metric for the given project
   *
   * @param projectId The id of the project.
   */
  void deleteProjectFilesCommitsAndMetrics(Long projectId);
}
