package org.wickedsource.coderadar.projectadministration.port.driven.project;

import org.wickedsource.coderadar.projectadministration.domain.Project;

public interface DeleteProjectPort {
  void delete(Long id);
  void delete(Project project);
}
