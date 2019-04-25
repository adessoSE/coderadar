package io.reflectoring.coderadar.core.projectadministration.port.driver.module.update;

public interface UpdateModuleUseCase {
  void updateModule(UpdateModuleCommand command, Long moduleId);
}
