package io.reflectoring.coderadar.core.projectadministration.port.driver.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;

import java.util.List;

public interface ListModulesOfProjectUseCase {
  List<Module> listModules(ListModulesOfProjectCommand command);
}
