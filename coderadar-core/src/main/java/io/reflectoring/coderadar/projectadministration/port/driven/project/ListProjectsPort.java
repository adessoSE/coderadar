package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import java.util.Collection;
import java.util.List;

public interface ListProjectsPort {

  /** @return All project currently saved in the database. */
  Collection<Project> getProjects();

  /** @return All project currently saved in the database. */
  List<GetProjectResponse> getProjectResponses();
}
