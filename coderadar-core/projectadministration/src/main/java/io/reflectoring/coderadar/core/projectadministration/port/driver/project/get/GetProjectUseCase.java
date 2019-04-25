package io.reflectoring.coderadar.core.projectadministration.port.driver.project.get;

public interface GetProjectUseCase {
  GetProjectResponse get(Long projectId);
}
