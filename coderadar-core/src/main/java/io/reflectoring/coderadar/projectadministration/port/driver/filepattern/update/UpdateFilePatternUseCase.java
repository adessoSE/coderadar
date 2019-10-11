package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;

public interface UpdateFilePatternUseCase {
  void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId)
      throws FilePatternNotFoundException;
}
