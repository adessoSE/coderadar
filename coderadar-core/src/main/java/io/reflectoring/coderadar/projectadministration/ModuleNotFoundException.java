package io.reflectoring.coderadar.projectadministration;

public class ModuleNotFoundException extends EntityNotFoundException {
  public ModuleNotFoundException(Long moduleId) {
    super("Module with id " + moduleId + " not found.");
  }
}
