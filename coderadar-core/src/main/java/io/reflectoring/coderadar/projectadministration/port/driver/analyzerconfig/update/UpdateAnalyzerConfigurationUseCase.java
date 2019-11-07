package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update;

public interface UpdateAnalyzerConfigurationUseCase {
  void update(UpdateAnalyzerConfigurationCommand command, Long analyzerId, Long projectId);
}
