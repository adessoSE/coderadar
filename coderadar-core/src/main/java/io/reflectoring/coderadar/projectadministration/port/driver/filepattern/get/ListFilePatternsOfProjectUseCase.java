package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

import java.util.List;

public interface ListFilePatternsOfProjectUseCase {

  /**
   * Retrieves all file pattern for a given project
   *
   * @param projectId The project id.
   * @return All file patterns in the project.
   */
  List<GetFilePatternResponse> listFilePatterns(Long projectId);
}
