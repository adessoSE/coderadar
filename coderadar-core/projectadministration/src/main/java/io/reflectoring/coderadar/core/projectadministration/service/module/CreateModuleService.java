package io.reflectoring.coderadar.core.projectadministration.service.module;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.module.create.CreateModuleUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CreateModuleService")
public class CreateModuleService implements CreateModuleUseCase {
  private final GetProjectPort getProjectPort;
  private final CreateModulePort createModulePort;

  @Autowired
  public CreateModuleService(
      @Qualifier("GetProjectServiceNeo4j") GetProjectPort getProjectPort,
      @Qualifier("CreateModuleServiceNeo4j") CreateModulePort createModulePort) {
    this.getProjectPort = getProjectPort;
    this.createModulePort = createModulePort;
  }

  @Override
  public Long createModule(CreateModuleCommand command, Long projectId) {
    Module module = new Module();
    module.setPath(command.getPath());
    Optional<Project> project = getProjectPort.get(projectId);

    if (project.isPresent()) {
      module.setProject(project.get());
    } else {
      throw new ProjectNotFoundException();
    }

    return createModulePort.createModule(module);
  }
}
