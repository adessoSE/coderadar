package io.reflectoring.coderadar.core.analyzer.port.driven;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;

public interface StartAnalyzingPort {
  Long start(Long projectId, AnalyzingJob analyzingJob);
}
