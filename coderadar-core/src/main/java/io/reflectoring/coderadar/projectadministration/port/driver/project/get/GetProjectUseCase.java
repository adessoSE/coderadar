package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;

public interface GetProjectUseCase {

  /**
   * @param projectId The id of the project.
   * @return The project with the supplied id.
   */
  Project get(long projectId);

  /**
   * @param projectId The id of the project.
   * @return The project with the supplied id.
   */
  ProjectWithRoles getWithRoles(long projectId, long userId);
}
