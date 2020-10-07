package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;

public class FilePatternAlreadyExistsException extends EntityAlreadyExistsException {
  public FilePatternAlreadyExistsException(InclusionType inclusionType, String pattern) {
    super(
        String.format(
            "File pattern with inclusion type %s and pattern %s already exists!",
            inclusionType.toString(), pattern));
  }
}
