package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectPort {
  List<AnalyzerConfiguration> get(Long projectId);
}
