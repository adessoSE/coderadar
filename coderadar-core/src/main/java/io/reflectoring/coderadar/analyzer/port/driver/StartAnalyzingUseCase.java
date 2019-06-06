package io.reflectoring.coderadar.analyzer.port.driver;

public interface StartAnalyzingUseCase {
  void start(StartAnalyzingCommand command, Long projectId);
}
