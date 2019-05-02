package io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;

import java.util.Optional;

public interface GetAnalyzerConfigurationPort {
  Optional<AnalyzerConfiguration> getAnalyzerConfiguration(Long id);
}
