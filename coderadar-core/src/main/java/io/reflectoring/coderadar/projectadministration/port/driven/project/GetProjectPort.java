package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface GetProjectPort {
  Project get(Long id) throws ProjectNotFoundException;

  Project get(String name) throws ProjectNotFoundException;

  boolean existsByName(String name);

    boolean existsById(Long projectId);
}
