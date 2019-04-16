package io.reflectoring.coderadar.core.analyzer.port.driver;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzingJob;

public interface GetAnalyzingStatusUseCase {
  AnalyzingJob get(Long projectId);
}
