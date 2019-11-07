package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete;

public interface DeleteAnalyzerConfigurationUseCase {
  void deleteAnalyzerConfiguration(Long id, Long projectId);
}
