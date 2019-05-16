package io.reflectoring.coderadar.core.projectadministration.port.driver.module.get;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;

import java.util.List;

public interface ListModulesOfProjectUseCase {
  List<GetModuleResponse> listModules(Long projectId) throws ProjectNotFoundException;
}
