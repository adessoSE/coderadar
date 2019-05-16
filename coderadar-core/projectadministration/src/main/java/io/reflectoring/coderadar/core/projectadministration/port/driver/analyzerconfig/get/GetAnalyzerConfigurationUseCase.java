package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;

public interface GetAnalyzerConfigurationUseCase {
  GetAnalyzerConfigurationResponse getSingleAnalyzerConfiguration(Long id) throws AnalyzerConfigurationNotFoundException;
}
