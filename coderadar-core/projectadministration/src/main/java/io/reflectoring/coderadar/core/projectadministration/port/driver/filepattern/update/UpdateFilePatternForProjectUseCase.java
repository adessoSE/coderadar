package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update;

public interface UpdateFilePatternForProjectUseCase {
  void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId);
}
