package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface DeleteProjectPort {
  void delete(Long id);

  void delete(Project project);
}
