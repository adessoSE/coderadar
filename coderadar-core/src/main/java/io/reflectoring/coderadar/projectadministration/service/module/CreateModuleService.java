package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.SaveModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleService implements CreateModuleUseCase {

  private final CreateModulePort createModulePort;
  private final SaveModulePort saveModulePort;
  private final ProjectStatusPort projectStatusPort;
  private final Logger logger = LoggerFactory.getLogger(CreateProjectService.class);

  @Autowired
  public CreateModuleService(
      CreateModulePort createModulePort,
      SaveModulePort saveModulePort,
      ProjectStatusPort projectStatusPort) {
    this.createModulePort = createModulePort;
    this.saveModulePort = saveModulePort;
    this.projectStatusPort = projectStatusPort;
  }

  @Override
  public Long createModule(CreateModuleCommand command, Long projectId)
      throws ProjectNotFoundException, ModulePathInvalidException, ModuleAlreadyExistsException,
          ProjectIsBeingProcessedException {

    if (projectStatusPort.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    }

    projectStatusPort.setBeingProcessed(projectId, true);
    Long moduleId;

    try {
      Module module = new Module();
      module.setPath(command.getPath());
      moduleId = saveModulePort.saveModule(module, projectId);
      createModulePort.createModule(moduleId, projectId);
    } finally {
      projectStatusPort.setBeingProcessed(projectId, false);
    }
    return moduleId;
  }
}
