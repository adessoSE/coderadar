package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;

import java.util.Optional;

public interface GetAnalyzerConfigurationUseCase {
  Optional<AnalyzerConfiguration> getSingleAnalyzerConfiguration(GetAnalyzerConfigurationCommand command);
}
