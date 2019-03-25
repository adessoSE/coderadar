package org.wickedsource.coderadar.projectadministration.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Module;
import org.wickedsource.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.module.ListModulesOfProjectUseCase;

import java.util.List;

@Service
public class ListModulesOfProjectService implements ListModulesOfProjectUseCase {

  private final ListModulesOfProjectPort port;

  @Autowired
  public ListModulesOfProjectService(ListModulesOfProjectPort port) {
    this.port = port;
  }

  @Override
  public List<Module> listModules(Long projectId) {
    return port.listModules(projectId);
  }
}
