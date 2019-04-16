package io.reflectoring.coderadar.core.projectadministration.port.driven.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;

import java.util.List;

public interface ListModulesOfProjectPort {
    List<Module> listModules(Long projectId);
}
