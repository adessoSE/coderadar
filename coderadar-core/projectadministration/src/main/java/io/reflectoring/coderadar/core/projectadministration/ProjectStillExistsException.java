package io.reflectoring.coderadar.core.projectadministration;

public class ProjectStillExistsException extends RuntimeException {
  public ProjectStillExistsException(String name) {
    super("The project " + name + " still exists.");
  }
}
