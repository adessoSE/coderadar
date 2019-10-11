package io.reflectoring.coderadar.analyzer.port.driver;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;

public interface StartAnalyzingUseCase {
  void start(StartAnalyzingCommand command, Long projectId) throws ProjectIsBeingProcessedException;
}
