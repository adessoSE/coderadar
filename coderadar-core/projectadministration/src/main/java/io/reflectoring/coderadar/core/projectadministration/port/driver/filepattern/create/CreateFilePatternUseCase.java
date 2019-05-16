package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;

public interface CreateFilePatternUseCase {
  Long createFilePattern(CreateFilePatternCommand command, Long projectId)
      throws ProjectNotFoundException;
}
