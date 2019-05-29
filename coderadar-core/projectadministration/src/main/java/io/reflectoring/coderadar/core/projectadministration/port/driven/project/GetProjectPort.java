package io.reflectoring.coderadar.core.projectadministration.port.driven.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;

import java.util.Optional;

public interface GetProjectPort {
  Optional<Project> get(Long id);

  Optional<Project> get(String name);
}
