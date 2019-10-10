package io.reflectoring.coderadar.projectadministration.port.driver.module.get;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.List;

public interface ListModulesOfProjectUseCase {
  List<GetModuleResponse> listModules(Long projectId) throws ProjectNotFoundException;
}
