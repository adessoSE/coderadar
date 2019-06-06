package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import java.util.List;

public interface ListModulesOfProjectPort {
  List<Module> listModules(Long projectId);
}
