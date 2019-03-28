package org.wickedsource.coderadar.projectadministration.port.driver.module;

import java.util.List;
import org.wickedsource.coderadar.projectadministration.domain.Module;

public interface ListModulesOfProjectUseCase {
  List<Module> listModules(ListModulesOfProjectCommand command);
}
