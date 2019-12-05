package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import java.util.Collection;
import java.util.List;

public interface ListModulesOfProjectPort {

  /**
   * Retrieves all module in a project given the project id.
   *
   * @param projectId The id of the project.
   * @return All modules in the project.
   */
  Collection<Module> listModules(Long projectId);

  /**
   * Retrieves all module in a project given the project id.
   *
   * @param projectId The id of the project.
   * @return All modules in the project.
   */
  List<GetModuleResponse> listModuleResponses(Long projectId);
}
