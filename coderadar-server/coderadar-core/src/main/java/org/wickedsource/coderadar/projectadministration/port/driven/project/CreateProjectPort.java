package org.wickedsource.coderadar.projectadministration.port.driven.project;

import org.wickedsource.coderadar.project.domain.Project;

public interface CreateProjectPort {
  Project createProject(Project project);
}
