package io.reflectoring.coderadar.analyzer.port.driven;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;

public interface StartAnalyzingPort {
  Long start(StartAnalyzingCommand command, Long projectId);
}
