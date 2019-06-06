package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import java.util.Optional;

public interface GetAnalyzerConfigurationPort {
  Optional<AnalyzerConfiguration> getAnalyzerConfiguration(Long id);
}
