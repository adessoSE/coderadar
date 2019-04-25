package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update;

public interface UpdateAnalyzerConfigurationUseCase {
  void update(UpdateAnalyzerConfigurationCommand command, Long analyzerId);
}
