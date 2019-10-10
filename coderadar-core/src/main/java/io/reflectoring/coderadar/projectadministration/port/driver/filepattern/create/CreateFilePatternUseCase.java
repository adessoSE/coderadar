package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;

public interface CreateFilePatternUseCase {
  Long createFilePattern(CreateFilePatternCommand command, Long projectId)
      throws ProjectNotFoundException;
}
