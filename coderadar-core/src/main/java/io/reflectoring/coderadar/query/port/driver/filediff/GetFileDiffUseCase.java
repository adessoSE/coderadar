package io.reflectoring.coderadar.query.port.driver.filediff;

import io.reflectoring.coderadar.domain.FileContentWithMetrics;

public interface GetFileDiffUseCase {

  /**
   * @param projectId The id of the project.
   * @param command The command containing the needed parameters.
   * @return The diff for the given file against the same file in the previous (parent) commit.
   *     Metrics are set to null.
   */
  FileContentWithMetrics getFileDiff(long projectId, GetFileDiffCommand command);
}
