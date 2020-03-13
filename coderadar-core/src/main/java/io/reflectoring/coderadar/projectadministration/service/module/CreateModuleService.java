package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateModuleService implements CreateModuleUseCase {

  private final CreateModulePort createModulePort;
  private final ProjectStatusPort projectStatusPort;
  private final GetProjectPort getProjectPort;

  private final Logger logger = LoggerFactory.getLogger(CreateModuleService.class);

  public CreateModuleService(
      CreateModulePort createModulePort,
      ProjectStatusPort projectStatusPort,
      GetProjectPort getProjectPort) {
    this.createModulePort = createModulePort;
    this.projectStatusPort = projectStatusPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public Long createModule(CreateModuleCommand command, long projectId)
      throws ModulePathInvalidException {

    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (projectStatusPort.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    }

    projectStatusPort.setBeingProcessed(projectId, true);
    Long moduleId;
    try {
      moduleId = createModulePort.createModule(command.getPath(), projectId);
      logger.info(
          "Created module with path {} for project with id {}", command.getPath(), projectId);
    } finally {
      projectStatusPort.setBeingProcessed(projectId, false);
    }
    return moduleId;
  }
}
