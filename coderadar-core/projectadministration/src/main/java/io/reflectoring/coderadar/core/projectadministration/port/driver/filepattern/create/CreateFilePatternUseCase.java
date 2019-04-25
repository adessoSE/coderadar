package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create;

public interface CreateFilePatternUseCase {
  Long createFilePattern(CreateFilePatternCommand command, Long projectId);
}
