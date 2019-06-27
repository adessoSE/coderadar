package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface ListProjectsPort {
  Iterable<Project> getProjects();
}
