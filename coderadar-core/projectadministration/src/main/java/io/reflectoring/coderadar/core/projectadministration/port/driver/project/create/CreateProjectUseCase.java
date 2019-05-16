package io.reflectoring.coderadar.core.projectadministration.port.driver.project.create;

import java.net.MalformedURLException;

public interface CreateProjectUseCase {
  Long createProject(CreateProjectCommand command) throws MalformedURLException;
}
