package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
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
    Project persistedProject = port.get(id);

    GetProjectResponse response = new GetProjectResponse();
    response.setId(persistedProject.getId());
    response.setName(persistedProject.getName());
    response.setVcsUsername(persistedProject.getVcsUsername());
    response.setVcsPassword(persistedProject.getVcsPassword());
    response.setVcsOnline(persistedProject.isVcsOnline());
    response.setVcsUrl(persistedProject.getVcsUrl());
    response.setStartDate(persistedProject.getVcsStart());
    response.setEndDate(persistedProject.getVcsEnd());
    return response;
  }
}
