package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

public interface GetAnalyzerConfigurationPort {

  /**
   * Retrieves an analyzer configuration given its id.
   *
   * @param id The id of the configuration.
   * @return The configuration.
   */
  AnalyzerConfiguration getAnalyzerConfiguration(long id);

  /**
   * @param configurationId The analyzer configuration id.
   * @return True if an analyzer configuration with the given id exists.
   */
  boolean existsById(long configurationId);
}
