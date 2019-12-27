package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;

public interface GetProjectPort {

  /**
   * @param id The id of the project.
   * @return The project with the supplied id.
   */
  Project get(Long id);

  /**
   * @param name The name of the project.
   * @return The project with the supplied name.
   */
  Project get(String name);

  /**
   * @param name Name to check
   * @return True if a project with the given name exists, false otherwise
   */
  boolean existsByName(String name);

  /**
   * @param projectId id to check
   * @return True if a project with the given id exists, false otherwise
   */
  boolean existsById(Long projectId);

  /**
   * @param id The id of the project.
   * @return The project with the supplied id.
   */
  GetProjectResponse getProjectResponse(Long id);
}
