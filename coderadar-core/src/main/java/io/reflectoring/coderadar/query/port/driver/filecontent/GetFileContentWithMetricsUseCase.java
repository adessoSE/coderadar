package io.reflectoring.coderadar.query.port.driver.filecontent;

import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;

public interface GetFileContentWithMetricsUseCase {

  /**
   * @param projectId The id of the project.
   * @param command The command containing the commit hash and filepath.
   * @return The content of the file as a string and all of the metrics for the file along with
   *     their location in it.
   */
  FileContentWithMetrics getFileContentWithMetrics(
      long projectId, GetFileContentWithMetricsCommand command);
}
