package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update;

public interface UpdateFilePatternUseCase {
  void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId);
}
