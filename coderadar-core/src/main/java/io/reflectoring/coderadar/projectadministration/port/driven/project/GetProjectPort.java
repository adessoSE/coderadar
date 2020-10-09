package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;

public interface GetProjectPort {

  /**
   * @param id The id of the project.
   * @return The project with the supplied id.
   */
  Project get(long id);

  /**
   * @param name The name of the project.
   * @return The project with the supplied name.
   */
  Project get(String name);

  /**
   * @param projectId The project id.
   * @param userId The user id.
   * @return The project with the given id and all roles the user (with the given id) has for this
   *     project.
   */
  ProjectWithRoles getWithRoles(long projectId, long userId);

  /**
   * @param name Name to check
   * @return True if a project with the given name exists, false otherwise
   */
  boolean existsByName(String name);

  /**
   * @param projectId id to check
   * @return True if a project with the given id exists, false otherwise
   */
  boolean existsById(long projectId);
}
