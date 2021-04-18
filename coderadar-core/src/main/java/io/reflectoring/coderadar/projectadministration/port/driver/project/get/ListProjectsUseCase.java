package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

import io.reflectoring.coderadar.domain.Project;
import java.util.List;

public interface ListProjectsUseCase {

  /** @return All project currently saved in the database. */
  List<Project> listProjects();
}
