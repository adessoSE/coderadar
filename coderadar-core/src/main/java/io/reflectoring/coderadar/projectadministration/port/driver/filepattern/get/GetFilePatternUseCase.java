package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface GetFilePatternUseCase {

  /**
   * Retrieves a file pattern given its id.
   *
   * @param filePatternId The id of the pattern
   * @return The FilePattern
   */
  FilePattern get(long filePatternId);
}
