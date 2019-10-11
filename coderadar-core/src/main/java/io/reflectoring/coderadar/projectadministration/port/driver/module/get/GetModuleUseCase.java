package io.reflectoring.coderadar.projectadministration.port.driver.module.get;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;

public interface GetModuleUseCase {
  GetModuleResponse get(Long id) throws ModuleNotFoundException;
}
