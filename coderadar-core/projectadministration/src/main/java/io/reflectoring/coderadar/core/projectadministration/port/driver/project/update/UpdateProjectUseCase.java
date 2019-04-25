package io.reflectoring.coderadar.core.projectadministration.port.driver.project.update;

public interface UpdateProjectUseCase {
  void update(UpdateProjectCommand command, Long projectId);
}
