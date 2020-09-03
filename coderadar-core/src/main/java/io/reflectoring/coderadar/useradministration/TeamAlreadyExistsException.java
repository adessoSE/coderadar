package io.reflectoring.coderadar.useradministration;

import io.reflectoring.coderadar.EntityAlreadyExistsException;

public class TeamAlreadyExistsException extends EntityAlreadyExistsException {
  public TeamAlreadyExistsException(String name) {
    super("Team with name " + name + " already exists!");
  }
}
