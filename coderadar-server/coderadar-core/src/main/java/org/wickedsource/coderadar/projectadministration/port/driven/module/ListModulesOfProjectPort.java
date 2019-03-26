package org.wickedsource.coderadar.projectadministration.port.driven.module;

import java.util.List;
import org.wickedsource.coderadar.projectadministration.domain.Module;

public interface ListModulesOfProjectPort {
  List<Module> listModules(Long projectId);
}
