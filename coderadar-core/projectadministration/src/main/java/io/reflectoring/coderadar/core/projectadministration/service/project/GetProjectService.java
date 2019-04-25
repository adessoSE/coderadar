package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetProjectService implements GetProjectUseCase {

  private final GetProjectPort port;

  public GetProjectService(GetProjectPort port) {
    this.port = port;
  }

  @Override
  public GetProjectResponse get(Long id) {
    Project project = port.get(id);
    GetProjectResponse response = new GetProjectResponse();
    response.setName(project.getName());
    response.setVcsUsername(project.getVcsCoordinates().getUsername());
    response.setVcsPassword(project.getVcsCoordinates().getPassword());
    response.setVcsOnline(project.getVcsCoordinates().isOnline());
    response.setVcsUrl(project.getVcsCoordinates().getUrl());
    response.setStart(project.getVcsCoordinates().getStartDate());
    response.setEnd(project.getVcsCoordinates().getEndDate());
    return response;
  }
}
