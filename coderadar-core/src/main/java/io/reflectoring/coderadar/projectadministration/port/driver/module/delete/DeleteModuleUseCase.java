package io.reflectoring.coderadar.projectadministration.port.driver.module.delete;

public interface DeleteModuleUseCase {
  void delete(Long id, Long projectId);
}
