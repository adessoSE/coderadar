package io.reflectoring.coderadar.projectadministration;

public class ModulePathDoesNotExistsException extends Exception {
    public ModulePathDoesNotExistsException(String path) {
        super("Path " + path + " not found in the project!");
    }
}
