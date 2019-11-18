package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

public interface GetAnalyzerConfigurationPort {

  /**
   * Retrieves an analyzer configuration given its id.
   *
   * @param id The id of the configuration.
   * @return The configuration.
   */
  AnalyzerConfiguration getAnalyzerConfiguration(Long id);
}
