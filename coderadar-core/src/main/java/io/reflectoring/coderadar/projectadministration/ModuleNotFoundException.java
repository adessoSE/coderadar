package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class ModuleNotFoundException extends EntityNotFoundException {
  public ModuleNotFoundException(long moduleId) {
    super("Module with id " + moduleId + " not found.");
  }
}
