package io.reflectoring.coderadar.core.projectadministration.port.driver.project.update;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import java.net.MalformedURLException;

public interface UpdateProjectUseCase {
  void update(UpdateProjectCommand command, Long projectId)
      throws ProjectNotFoundException, MalformedURLException;
}
