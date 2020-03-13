package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create;

public interface CreateFilePatternUseCase {

  /**
   * Creates a new file pattern.
   *
   * @param command The file pattern parameters to use.
   * @param projectId The id of the project.
   * @return The id of the file pattern.
   */
  Long createFilePattern(CreateFilePatternCommand command, long projectId);
}
