package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

public interface GetAnalyzerConfigurationPort {
  AnalyzerConfiguration getAnalyzerConfiguration(Long id);
}
