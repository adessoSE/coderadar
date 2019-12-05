package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update;

public interface UpdateFilePatternUseCase {

  /**
   * @param command The command containing the update pattern parameters
   * @param filePatternId The id of the file pattern.
   */
  void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId);
}
