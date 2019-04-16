package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.GetProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.GetProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetProjectService implements GetProjectUseCase {

  private final GetProjectPort port;

  public GetProjectService(GetProjectPort port) {
    this.port = port;
  }

  @Override
  public Project get(GetProjectCommand command) {
    return port.get(command.getId());
  }
}
