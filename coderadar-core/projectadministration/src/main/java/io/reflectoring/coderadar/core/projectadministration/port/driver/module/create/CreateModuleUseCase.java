package io.reflectoring.coderadar.core.projectadministration.port.driver.module.create;

public interface CreateModuleUseCase {
  Long createModule(CreateModuleCommand command, Long projectId);
}
