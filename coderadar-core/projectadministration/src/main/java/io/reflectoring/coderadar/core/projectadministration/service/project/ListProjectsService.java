package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.ListProjectsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ListProjectsService")
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
      resource.setId(project.getId());
      resource.setName(project.getName());
      resource.setVcsUsername(project.getVcsUsername());
      resource.setVcsPassword(project.getVcsPassword());
      resource.setVcsOnline(project.isVcsOnline());
      resource.setVcsUrl(project.getVcsUrl());
      resource.setStart(project.getVcsStart());
      resource.setEnd(project.getVcsEnd());
      response.add(resource);
    }
    return response;
  }
}
