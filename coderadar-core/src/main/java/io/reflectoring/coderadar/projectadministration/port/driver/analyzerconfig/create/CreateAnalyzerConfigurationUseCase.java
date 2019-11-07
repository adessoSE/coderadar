package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

public interface CreateAnalyzerConfigurationUseCase {
  Long create(CreateAnalyzerConfigurationCommand command, Long projectId);
}
