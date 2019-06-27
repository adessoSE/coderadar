package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;

public interface DeleteAnalyzerConfigurationPort {
  void deleteAnalyzerConfiguration(Long id) throws AnalyzerConfigurationNotFoundException;
}
