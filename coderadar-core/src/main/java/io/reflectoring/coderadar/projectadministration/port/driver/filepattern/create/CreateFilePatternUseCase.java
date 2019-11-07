package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create;

public interface CreateFilePatternUseCase {
  Long createFilePattern(CreateFilePatternCommand command, Long projectId);
}
