package io.reflectoring.coderadar.projectadministration.port.driver.project.delete;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;

public interface DeleteProjectUseCase {
  void delete(Long id) throws ProjectNotFoundException;
}
