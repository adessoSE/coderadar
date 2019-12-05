package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityAlreadyExistsException;

public class ModuleAlreadyExistsException extends EntityAlreadyExistsException {
  public ModuleAlreadyExistsException(String path) {
    super("Module with path " + path + " already exists!");
  }
}
