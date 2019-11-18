package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface UpdateFilePatternPort {

  /**
   * Updates a single file pattern.
   *
   * @param filePattern The updated pattern.
   */
  void updateFilePattern(FilePattern filePattern);
}
