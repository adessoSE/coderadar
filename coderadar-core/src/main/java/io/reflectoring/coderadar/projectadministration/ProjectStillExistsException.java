package io.reflectoring.coderadar.projectadministration;

public class ProjectStillExistsException extends RuntimeException {
  public ProjectStillExistsException(String name) {
    super("The project " + name + " still exists.");
  }
}
