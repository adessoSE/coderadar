package org.wickedsource.coderadar.projectadministration.port.driven.module;

import org.wickedsource.coderadar.projectadministration.domain.Module;

import java.util.List;

public interface ListModulesOfProjectPort {
    List<Module> listModules(Long projectId);
}
