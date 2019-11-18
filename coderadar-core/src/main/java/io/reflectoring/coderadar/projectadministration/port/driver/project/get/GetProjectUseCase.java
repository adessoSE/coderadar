package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

public interface GetProjectUseCase {

  /**
   * @param projectId The id of the project.
   * @return The project with the supplied id.
   */
  GetProjectResponse get(Long projectId);
}
