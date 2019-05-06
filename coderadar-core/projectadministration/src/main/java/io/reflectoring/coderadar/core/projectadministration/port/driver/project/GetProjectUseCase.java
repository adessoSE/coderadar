package io.reflectoring.coderadar.core.projectadministration.port.driver.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import java.util.Optional;

public interface GetProjectUseCase {
  Optional<Project> get(GetProjectCommand command);
}
