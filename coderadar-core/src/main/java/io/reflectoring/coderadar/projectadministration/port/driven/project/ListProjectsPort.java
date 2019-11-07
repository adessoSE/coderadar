package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;

import java.util.List;

public interface ListProjectsPort {
  Iterable<Project> getProjects();

  List<GetProjectResponse> getProjectResponses();
}
