package org.wickedsource.coderadar.projectadministration.port.driven.module;

import org.wickedsource.coderadar.projectadministration.domain.Module;

public interface DeleteModulePort {
  void delete(Long id);
  void delete(Module module);
}
