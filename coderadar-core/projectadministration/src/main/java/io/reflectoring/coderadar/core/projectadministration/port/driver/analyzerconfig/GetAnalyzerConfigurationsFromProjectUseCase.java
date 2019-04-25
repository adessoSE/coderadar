package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;

import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectUseCase {
  List<AnalyzerConfiguration> get(Long projectId);
}
