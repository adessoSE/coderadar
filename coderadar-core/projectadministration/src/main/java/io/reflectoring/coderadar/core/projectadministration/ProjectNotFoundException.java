package io.reflectoring.coderadar.core.projectadministration;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException() {}

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
