package org.wickedsource.coderadar.projectadministration.port.driver.project;

import org.wickedsource.coderadar.projectadministration.domain.Project;

public interface UpdateProjectUseCase {
  void update(UpdateProjectCommand command);
}
