package io.reflectoring.coderadar.projectadministration;

public class UnableToDeleteProjectException extends RuntimeException {
  public UnableToDeleteProjectException(Long projectId) {
    super("Project with id " + projectId + " cannot be deleted.");
  }
}
