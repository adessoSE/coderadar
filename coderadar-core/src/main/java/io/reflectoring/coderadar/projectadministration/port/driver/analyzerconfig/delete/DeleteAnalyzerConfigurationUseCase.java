package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete;

import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;

public interface DeleteAnalyzerConfigurationUseCase {
  void deleteAnalyzerConfiguration(Long id, Long projectId)
      throws AnalyzerConfigurationNotFoundException;
}
