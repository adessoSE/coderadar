package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ListModulesOfProjectService")
public class ListModulesOfProjectService implements ListModulesOfProjectUseCase {

  private final ListModulesOfProjectPort port;

  @Autowired
  public ListModulesOfProjectService(ListModulesOfProjectPort port) {
    this.port = port;
  }

  @Override
  public List<GetModuleResponse> listModules(Long projectId) {
    List<GetModuleResponse> modules = new ArrayList<>();
    for (Module module : port.listModules(projectId)) {
      modules.add(new GetModuleResponse(module.getId(), module.getPath()));
    }
    return modules;
  }
}
