package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException {
  public ProjectNotFoundException(long projectId) {
    super("Project with id " + projectId + " not found.");
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
