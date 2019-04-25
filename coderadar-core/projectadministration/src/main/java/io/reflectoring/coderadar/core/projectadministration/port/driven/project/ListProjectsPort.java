package io.reflectoring.coderadar.core.projectadministration.port.driven.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import java.util.List;

public interface ListProjectsPort {
  List<Project> getProjects();
}
