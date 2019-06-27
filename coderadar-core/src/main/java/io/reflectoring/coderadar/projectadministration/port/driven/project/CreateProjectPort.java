package io.reflectoring.coderadar.projectadministration.port.driven.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface CreateProjectPort {
  Long createProject(Project project);
}
