package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.CreateModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleService implements CreateModuleUseCase {

  private final GetProjectPort getProjectPort;
  private final CreateModulePort createModulePort;

  @Autowired
  public CreateModuleService(GetProjectPort getProjectPort, CreateModulePort createModulePort) {
    this.getProjectPort = getProjectPort;
    this.createModulePort = createModulePort;
  }

  @Override
  public Long createModule(CreateModuleCommand command) {
    Module module = new Module();
    module.setProject(getProjectPort.get(command.getProjectId()));
    module.setPath(command.getPath());
    module = createModulePort.createModule(module);
    return module.getId();
  }
}
