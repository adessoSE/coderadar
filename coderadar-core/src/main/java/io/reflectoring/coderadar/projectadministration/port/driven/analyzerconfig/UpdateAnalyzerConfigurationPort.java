package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

public interface UpdateAnalyzerConfigurationPort {

  /**
   * Updates a single analyzer configuration
   *
   * @param configuration The updated configuration.
   */
  void update(AnalyzerConfiguration configuration);
}
