package io.reflectoring.coderadar.core.analyzer.port.driven;

import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;

public interface StartAnalyzingPort {
  Long start(StartAnalyzingCommand command, Long projectId);
}
