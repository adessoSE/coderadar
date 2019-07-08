package io.reflectoring.coderadar.projectadministration;

public class ModuleAlreadyExistsException extends Exception {
    public ModuleAlreadyExistsException(String path) {
        super("Module with path " + path + " already exists!");
    }
}
