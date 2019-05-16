package io.reflectoring.coderadar.core.projectadministration.port.driver.module.create;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;

public interface CreateModuleUseCase {
  Long createModule(CreateModuleCommand command, Long projectId) throws ProjectNotFoundException;
}
