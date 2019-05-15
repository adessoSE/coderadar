package io.reflectoring.coderadar.core.projectadministration;

public class ModuleNotFoundException extends RuntimeException {
  public ModuleNotFoundException(Long moduleId) {
    super("Module with id " + moduleId + " not found.");
  }
}
