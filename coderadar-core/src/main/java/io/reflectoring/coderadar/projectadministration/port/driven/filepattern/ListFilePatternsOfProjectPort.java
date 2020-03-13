package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.List;

public interface ListFilePatternsOfProjectPort {
  /**
   * Retrieves all file pattern for a given project
   *
   * @param projectId The project id.
   * @return All file patterns in the project.
   */
  List<FilePattern> listFilePatterns(long projectId);
}
