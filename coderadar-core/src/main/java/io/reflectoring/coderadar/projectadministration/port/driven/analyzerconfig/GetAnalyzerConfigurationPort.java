package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface GetAnalyzerConfigurationPort {
  AnalyzerConfiguration getAnalyzerConfiguration(Long id)
      throws AnalyzerConfigurationNotFoundException;
}
