package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException {
  public ProjectNotFoundException(long projectId) {
    super(String.format("Project with id %d not found.", projectId));
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
