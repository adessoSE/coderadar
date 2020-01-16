package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface GetProjectUseCase {

  /**
   * @param projectId The id of the project.
   * @return The project with the supplied id.
   */
  Project get(Long projectId);
}
