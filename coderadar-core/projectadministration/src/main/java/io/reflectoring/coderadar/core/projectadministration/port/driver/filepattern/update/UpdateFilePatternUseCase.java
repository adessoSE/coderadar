package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update;

import io.reflectoring.coderadar.core.projectadministration.FilePatternNotFoundException;

public interface UpdateFilePatternUseCase {
  void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId)
      throws FilePatternNotFoundException;
}
