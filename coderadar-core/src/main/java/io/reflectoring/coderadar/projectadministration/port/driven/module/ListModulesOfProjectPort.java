package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.domain.Module;
import java.util.List;

public interface ListModulesOfProjectPort {

  /**
   * Retrieves all module in a project given the project id.
   *
   * @param projectId The id of the project.
   * @return All modules in the project.
   */
  List<Module> listModules(long projectId);
}
