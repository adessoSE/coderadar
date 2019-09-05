package io.reflectoring.coderadar.projectadministration;

public class ProjectIsBeingProcessedException extends RuntimeException {
  public ProjectIsBeingProcessedException(Long id) {
    super("Project with id " + id + " is currently being processed, cannot make changes!");
  }
}
