package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

public interface GetFilePatternUseCase {

  /**
   * Retrieves a file pattern given its id.
   *
   * @param filePatternId The id of the pattern
   * @return The FilePattern
   */
  GetFilePatternResponse get(Long filePatternId);
}
