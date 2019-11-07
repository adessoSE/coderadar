package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import java.util.Collection;
import java.util.List;

public interface ListModulesOfProjectPort {
  Collection<Module> listModules(Long projectId);

  List<GetModuleResponse> listModuleResponses(Long projectId);
}
