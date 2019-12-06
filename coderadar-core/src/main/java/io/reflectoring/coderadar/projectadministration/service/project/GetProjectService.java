package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetProjectService implements GetProjectUseCase {

  private final GetProjectPort port;

  public GetProjectService(GetProjectPort port) {
    this.port = port;
  }

  @Override
  public GetProjectResponse get(Long id) {
    return port.getProjectResponse(id);
  }
}
