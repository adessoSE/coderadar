package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update;

public interface UpdateFilePatternUseCase {
  void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId);
}
