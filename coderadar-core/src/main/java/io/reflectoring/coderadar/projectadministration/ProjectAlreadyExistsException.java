package io.reflectoring.coderadar.projectadministration;

public class ProjectAlreadyExistsException extends RuntimeException {
  public ProjectAlreadyExistsException(String name) {
    super("The project " + name + " already exists.");
  }
}
