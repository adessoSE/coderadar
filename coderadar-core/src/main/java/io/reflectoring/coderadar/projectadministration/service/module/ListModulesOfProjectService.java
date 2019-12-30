package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.ListModulesOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListModulesOfProjectService implements ListModulesOfProjectUseCase {

  private final ListModulesOfProjectPort port;
  private final GetProjectPort getProjectPort;

  public ListModulesOfProjectService(ListModulesOfProjectPort port, GetProjectPort getProjectPort) {
    this.port = port;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<Module> listModules(Long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return port.listModules(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
