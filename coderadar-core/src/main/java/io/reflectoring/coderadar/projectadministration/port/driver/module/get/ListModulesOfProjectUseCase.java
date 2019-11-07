package io.reflectoring.coderadar.projectadministration.port.driver.module.get;

import java.util.List;

public interface ListModulesOfProjectUseCase {
  List<GetModuleResponse> listModules(Long projectId);
}
