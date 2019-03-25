package org.wickedsource.coderadar.projectadministration.port.driver.module;

import org.wickedsource.coderadar.projectadministration.domain.Module;

import java.util.List;

public interface ListModulesOfProjectUseCase {
    List<Module> listModules(Long projectId);
}
