package io.reflectoring.coderadar.core.projectadministration;

public class ProjectNotFound extends RuntimeException {
  public ProjectNotFound() {}

  public ProjectNotFound(String message) {
    super(message);
  }
}
