package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;

public interface GetFileContentWithMetricsUseCase {

  FileContentWithMetrics getFileContentWithMetrics(
      long projectId, GetFileContentWithMetricsCommand command);
}
