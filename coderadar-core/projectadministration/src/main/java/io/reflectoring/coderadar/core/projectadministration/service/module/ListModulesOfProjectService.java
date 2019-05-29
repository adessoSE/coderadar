package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ListModulesOfProjectService")
public class ListModulesOfProjectService implements ListModulesOfProjectUseCase {

  private final ListModulesOfProjectPort port;
  private final GetProjectPort getProjectPort;

  @Autowired
  public ListModulesOfProjectService(
      @Qualifier("ListModulesOfProjectServiceNeo4j") ListModulesOfProjectPort port,
      GetProjectPort getProjectPort) {
    this.port = port;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<GetModuleResponse> listModules(Long projectId) throws ProjectNotFoundException {
    if (getProjectPort.get(projectId).isPresent()) {
      List<GetModuleResponse> modules = new ArrayList<>();
      for (Module module : port.listModules(projectId)) {
        modules.add(new GetModuleResponse(module.getId(), module.getPath()));
      }
      return modules;
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
