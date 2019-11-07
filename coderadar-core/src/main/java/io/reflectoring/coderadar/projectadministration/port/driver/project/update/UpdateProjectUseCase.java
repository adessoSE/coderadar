package io.reflectoring.coderadar.projectadministration.port.driver.project.update;

import java.net.MalformedURLException;

public interface UpdateProjectUseCase {
  void update(UpdateProjectCommand command, Long projectId) throws MalformedURLException;
}
