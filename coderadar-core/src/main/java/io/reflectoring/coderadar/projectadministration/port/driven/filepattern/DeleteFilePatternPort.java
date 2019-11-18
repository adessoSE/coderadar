package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

public interface DeleteFilePatternPort {

  /**
   * Deletes a file pattern from the DB given its id.
   *
   * @param id The id of the file pattern.
   */
  void delete(Long id);
}
