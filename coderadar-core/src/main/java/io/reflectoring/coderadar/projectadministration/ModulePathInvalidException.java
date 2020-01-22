package io.reflectoring.coderadar.projectadministration;

public class ModulePathInvalidException extends RuntimeException {
  public ModulePathInvalidException(String path) {
    super(path + " is not a valid path!");
  }
}
