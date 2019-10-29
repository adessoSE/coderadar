package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface UpdateProjectPort {
  void update(Project project);

  void deleteProjectFilesCommitsAndMetrics(Long projectId);
}
