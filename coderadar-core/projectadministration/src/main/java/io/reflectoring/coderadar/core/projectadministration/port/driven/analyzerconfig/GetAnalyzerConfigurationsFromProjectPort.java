package io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectPort {
  List<AnalyzerConfiguration> get(Long id);
}
