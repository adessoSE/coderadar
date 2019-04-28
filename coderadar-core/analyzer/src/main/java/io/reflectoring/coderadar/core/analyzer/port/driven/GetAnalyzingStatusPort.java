package io.reflectoring.coderadar.core.analyzer.port.driven;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;

public interface GetAnalyzingStatusPort {
  AnalyzingJob get(Long projectId);
}
