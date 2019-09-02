package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.List;

public interface GetProjectPort {
  Project get(Long id) throws ProjectNotFoundException;

  Project get(String name) throws ProjectNotFoundException;

  boolean existsByName(String name);

  boolean existsById(Long projectId);

  List<Project> findByName(String name);
}
