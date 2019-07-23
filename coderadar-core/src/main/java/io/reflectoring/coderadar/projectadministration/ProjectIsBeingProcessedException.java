package io.reflectoring.coderadar.projectadministration;

public class ProjectIsBeingProcessedException extends Exception {
  public ProjectIsBeingProcessedException(Long id) {
    super("Project with id " + id + " is currently being processed, cannot make changes!");
  }
}
