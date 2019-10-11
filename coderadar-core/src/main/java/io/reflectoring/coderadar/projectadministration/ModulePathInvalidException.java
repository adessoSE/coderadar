package io.reflectoring.coderadar.projectadministration;

public class ModulePathInvalidException extends Exception {
  public ModulePathInvalidException(String path) {
    super(path + " is not a valid path!");
  }
}
