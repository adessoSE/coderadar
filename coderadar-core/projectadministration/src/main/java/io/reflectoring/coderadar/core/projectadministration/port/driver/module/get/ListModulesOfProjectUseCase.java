package io.reflectoring.coderadar.core.projectadministration.port.driver.module.get;

import java.util.List;

public interface ListModulesOfProjectUseCase {
  List<GetModuleResponse> listModules(Long projectId);
}
