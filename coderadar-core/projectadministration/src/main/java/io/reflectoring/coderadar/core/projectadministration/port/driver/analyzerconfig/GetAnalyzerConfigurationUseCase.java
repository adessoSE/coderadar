package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;

public interface GetAnalyzerConfigurationUseCase {
  AnalyzerConfiguration getSingleAnalyzerConfiguration(GetAnalyzerConfigurationCommand command);
}
