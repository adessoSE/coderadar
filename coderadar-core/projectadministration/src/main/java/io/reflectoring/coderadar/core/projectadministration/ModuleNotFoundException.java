package io.reflectoring.coderadar.core.projectadministration;

public class ModuleNotFoundException extends RuntimeException {
  public ModuleNotFoundException() {}

  public ModuleNotFoundException(String message) {
    super(message);
  }
}
