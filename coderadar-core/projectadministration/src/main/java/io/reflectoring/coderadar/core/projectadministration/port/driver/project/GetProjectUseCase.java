package io.reflectoring.coderadar.core.projectadministration.port.driver.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;

public interface GetProjectUseCase {
  Project get(Long id);
}
