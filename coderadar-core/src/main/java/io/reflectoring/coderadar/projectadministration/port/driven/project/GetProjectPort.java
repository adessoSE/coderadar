package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import java.util.List;

public interface GetProjectPort {
  Project get(Long id);

  Project get(String name);

  boolean existsByName(String name);

  boolean existsById(Long projectId);

  List<Project> findByName(String name);

  GetProjectResponse getProjectResponse(Long id);
}
