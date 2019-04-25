package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.ListProjectsUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsService implements ListProjectsUseCase {

  private final ListProjectsPort listProjectsPort;

  @Autowired
  public ListProjectsService(ListProjectsPort listProjectsPort) {
    this.listProjectsPort = listProjectsPort;
  }

  @Override
  public List<GetProjectResponse> listProjects() {
    List<GetProjectResponse> response = new ArrayList<>();
    for (Project project : listProjectsPort.getProjects()) {
      GetProjectResponse resource = new GetProjectResponse();
      resource.setName(project.getName());
      resource.setVcsUsername(project.getVcsCoordinates().getUsername());
      resource.setVcsPassword(project.getVcsCoordinates().getPassword());
      resource.setVcsOnline(project.getVcsCoordinates().isOnline());
      resource.setVcsUrl(project.getVcsCoordinates().getUrl());
      resource.setStart(project.getVcsCoordinates().getStartDate());
      resource.setEnd(project.getVcsCoordinates().getEndDate());
      response.add(resource);
    }
    return response;
  }
}
