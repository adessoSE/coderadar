package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.delete;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;

public interface DeleteAnalyzerConfigurationUseCase {
  void deleteAnalyzerConfiguration(Long id) throws AnalyzerConfigurationNotFoundException;
}
