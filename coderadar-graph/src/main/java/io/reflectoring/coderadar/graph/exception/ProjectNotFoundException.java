package io.reflectoring.coderadar.graph.exception;

public class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException(String message) {
    super(message);
  }
}
