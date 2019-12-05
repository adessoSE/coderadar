package io.reflectoring.coderadar.projectadministration.port.driver.project.create;

import java.net.MalformedURLException;

public interface CreateProjectUseCase {

  /**
   * Creates a new project.
   *
   * @param command The project to create.
   * @return The DB id of the project.
   */
  Long createProject(CreateProjectCommand command) throws MalformedURLException;
}
