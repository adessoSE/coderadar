package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class FilePatternNotFoundException extends EntityNotFoundException {
  public FilePatternNotFoundException(long id) {
    super("FilePattern with id " + id + " not found.");
  }
}
