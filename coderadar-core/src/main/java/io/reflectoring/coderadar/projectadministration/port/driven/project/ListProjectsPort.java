package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.domain.Project;
import java.util.List;

public interface ListProjectsPort {

  /** @return All project currently saved in the database. */
  List<Project> getProjects();
}
