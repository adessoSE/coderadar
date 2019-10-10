package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get;

import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;

public interface GetAnalyzerConfigurationUseCase {
  GetAnalyzerConfigurationResponse getSingleAnalyzerConfiguration(Long id)
      throws AnalyzerConfigurationNotFoundException;
}
