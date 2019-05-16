package io.reflectoring.coderadar.core.projectadministration.port.driver.project.delete;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;

public interface DeleteProjectUseCase {
  void delete(Long id) throws ProjectNotFoundException;
}
