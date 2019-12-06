package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete;

public interface DeleteFilePatternFromProjectUseCase {

  /**
   * Deletes a file pattern from the DB given its id.
   *
   * @param filePatternId The id of the file pattern.
   * @param projectId The id of the project.
   */
  void delete(Long filePatternId, Long projectId);
}
