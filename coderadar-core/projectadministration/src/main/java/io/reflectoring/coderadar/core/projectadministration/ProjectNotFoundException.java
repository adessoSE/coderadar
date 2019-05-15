package io.reflectoring.coderadar.core.projectadministration;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException(Long projectId) {
    super("Project with id " + projectId + " not found.");
  }
}
