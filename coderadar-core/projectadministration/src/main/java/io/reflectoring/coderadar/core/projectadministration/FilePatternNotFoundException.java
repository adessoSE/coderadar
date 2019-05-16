package io.reflectoring.coderadar.core.projectadministration;

public class FilePatternNotFoundException  extends RuntimeException {
    public FilePatternNotFoundException(Long id) {
        super("FilePattern with id " + id + " not found.");
    }
}
