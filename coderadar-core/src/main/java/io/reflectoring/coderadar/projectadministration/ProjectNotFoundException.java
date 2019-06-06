package io.reflectoring.coderadar.projectadministration;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException(Long projectId) {
    super("Project with id " + projectId + " not found.");
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
