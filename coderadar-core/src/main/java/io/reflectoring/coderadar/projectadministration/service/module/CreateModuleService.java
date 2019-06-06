package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CreateModuleService")
public class CreateModuleService implements CreateModuleUseCase {
  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  private final CreateModulePort createModulePort;

  @Autowired
  public CreateModuleService(
      @Qualifier("GetProjectServiceNeo4j") GetProjectPort getProjectPort,
      UpdateProjectPort updateProjectPort,
      @Qualifier("CreateModuleServiceNeo4j") CreateModulePort createModulePort) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
    this.createModulePort = createModulePort;
  }

  @Override
  public Long createModule(CreateModuleCommand command, Long projectId)
      throws ProjectNotFoundException {
    Module module = new Module();
    module.setPath(command.getPath());
    Optional<Project> project = getProjectPort.get(projectId);

    if (project.isPresent()) {
      module.setProject(project.get());
      project.get().getModules().add(module);
      updateProjectPort.update(project.get());
    } else {
      throw new ProjectNotFoundException(projectId);
    }

    return createModulePort.createModule(module);
  }
}
