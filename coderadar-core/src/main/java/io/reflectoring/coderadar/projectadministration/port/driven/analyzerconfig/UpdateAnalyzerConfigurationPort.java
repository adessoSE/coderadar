package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

public interface UpdateAnalyzerConfigurationPort {
  void update(AnalyzerConfiguration configuration);
}
