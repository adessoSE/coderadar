package io.reflectoring.coderadar.core.projectadministration.port.driven.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;

public interface ListProjectsPort {
  Iterable<Project> getProjects();
}
