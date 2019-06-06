package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
