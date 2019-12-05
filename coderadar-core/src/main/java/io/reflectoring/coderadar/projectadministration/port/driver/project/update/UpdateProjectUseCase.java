package io.reflectoring.coderadar.projectadministration.port.driver.project.update;

import java.net.MalformedURLException;

public interface UpdateProjectUseCase {

  /**
   * Updates an existing project.
   *
   * @param command The updated project.
   * @param projectId The id of the project.
   */
  void update(UpdateProjectCommand command, Long projectId) throws MalformedURLException;
}
