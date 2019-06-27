package io.reflectoring.coderadar.projectadministration.port.driver.module.create;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;

public interface CreateModuleUseCase {
  Long createModule(CreateModuleCommand command, Long projectId) throws ProjectNotFoundException;
}
