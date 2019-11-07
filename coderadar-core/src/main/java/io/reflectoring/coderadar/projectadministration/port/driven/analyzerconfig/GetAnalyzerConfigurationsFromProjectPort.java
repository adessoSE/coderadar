package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import java.util.Collection;

public interface GetAnalyzerConfigurationsFromProjectPort {
  Collection<AnalyzerConfiguration> get(Long projectId);
}
