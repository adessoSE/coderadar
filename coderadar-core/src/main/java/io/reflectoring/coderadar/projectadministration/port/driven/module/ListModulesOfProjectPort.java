package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import java.util.Collection;

public interface ListModulesOfProjectPort {
  Collection<Module> listModules(Long projectId) throws ProjectNotFoundException;
}
