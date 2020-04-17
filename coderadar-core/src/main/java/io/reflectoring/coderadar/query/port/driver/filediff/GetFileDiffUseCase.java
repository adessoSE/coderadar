package io.reflectoring.coderadar.query.port.driver.filediff;

import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;

public interface GetFileDiffUseCase {

  FileContentWithMetrics getFileDiff(
      long projectId, GetFileDiffCommand command);
}
