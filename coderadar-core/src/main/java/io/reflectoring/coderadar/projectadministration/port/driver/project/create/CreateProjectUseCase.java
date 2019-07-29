package io.reflectoring.coderadar.projectadministration.port.driver.project.create;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;

import java.net.MalformedURLException;

public interface CreateProjectUseCase {
  Long createProject(CreateProjectCommand command) throws MalformedURLException, ProjectIsBeingProcessedException;
}
