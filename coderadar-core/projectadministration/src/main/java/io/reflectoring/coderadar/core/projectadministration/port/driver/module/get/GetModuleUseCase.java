package io.reflectoring.coderadar.core.projectadministration.port.driver.module.get;

import io.reflectoring.coderadar.core.projectadministration.ModuleNotFoundException;

public interface GetModuleUseCase {
  GetModuleResponse get(Long id) throws ModuleNotFoundException;
}
