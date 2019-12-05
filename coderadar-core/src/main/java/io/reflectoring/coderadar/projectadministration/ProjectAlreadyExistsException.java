package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityAlreadyExistsException;

public class ProjectAlreadyExistsException extends EntityAlreadyExistsException {
  public ProjectAlreadyExistsException(String name) {
    super("The project " + name + " already exists.");
  }
}
