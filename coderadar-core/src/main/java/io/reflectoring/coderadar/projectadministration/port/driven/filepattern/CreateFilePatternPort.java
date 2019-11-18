package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;

public interface CreateFilePatternPort {
  /**
   * Saves a file pattern in the DB
   *
   * @param filePattern The file pattern save.
   * @param projectId The id of the project.
   * @return The DB id of the file pattern.
   */
  Long createFilePattern(FilePattern filePattern, Long projectId);
}
