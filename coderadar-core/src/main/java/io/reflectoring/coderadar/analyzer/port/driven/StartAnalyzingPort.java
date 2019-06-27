package io.reflectoring.coderadar.analyzer.port.driven;

import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;

public interface StartAnalyzingPort {
  Long start(StartAnalyzingCommand command, Long projectId) throws ProjectNotFoundException;
}
