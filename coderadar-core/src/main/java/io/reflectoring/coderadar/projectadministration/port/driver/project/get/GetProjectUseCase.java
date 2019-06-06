package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

public interface GetProjectUseCase {
  GetProjectResponse get(Long projectId);
}
