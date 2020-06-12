package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ListProjectsPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProjectsService implements ListProjectsUseCase {

  private final ListProjectsPort listProjectsPort;

  public ListProjectsService(ListProjectsPort listProjectsPort) {
    this.listProjectsPort = listProjectsPort;
  }

  @Override
  public List<Project> listProjects() {
    return listProjectsPort.getProjects();
  }
}
