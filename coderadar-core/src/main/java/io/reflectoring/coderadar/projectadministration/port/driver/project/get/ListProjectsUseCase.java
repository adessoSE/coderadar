package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

import java.util.List;

public interface ListProjectsUseCase {

  /** @return All project currently saved in the database. */
  List<GetProjectResponse> listProjects();
}
