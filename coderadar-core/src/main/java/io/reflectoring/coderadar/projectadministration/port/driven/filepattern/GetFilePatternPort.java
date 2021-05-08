package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.domain.FilePattern;

public interface GetFilePatternPort {

  /**
   * Retrieves a file pattern given its id.
   *
   * @param id The id of the pattern
   * @return The FilePattern
   */
  FilePattern get(long id);
}
