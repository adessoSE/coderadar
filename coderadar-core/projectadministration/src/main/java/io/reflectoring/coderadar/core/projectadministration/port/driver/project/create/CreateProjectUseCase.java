package io.reflectoring.coderadar.core.projectadministration.port.driver.project.create;

public interface CreateProjectUseCase {
  Long createProject(CreateProjectCommand command);
}
