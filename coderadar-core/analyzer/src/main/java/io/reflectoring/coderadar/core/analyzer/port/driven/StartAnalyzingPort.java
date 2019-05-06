package io.reflectoring.coderadar.core.analyzer.port.driven;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;

public interface StartAnalyzingPort {
  Long start(Long projectId, AnalyzingJob analyzingJob);
}
