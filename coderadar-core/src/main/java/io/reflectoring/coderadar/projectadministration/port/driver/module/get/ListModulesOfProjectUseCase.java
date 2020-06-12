package io.reflectoring.coderadar.projectadministration.port.driver.module.get;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import java.util.List;

public interface ListModulesOfProjectUseCase {

  /**
   * Retrieves all module in a project given the project id.
   *
   * @param projectId The id of the project.
   * @return All modules in the project.
   */
  List<Module> listModules(long projectId);
}
