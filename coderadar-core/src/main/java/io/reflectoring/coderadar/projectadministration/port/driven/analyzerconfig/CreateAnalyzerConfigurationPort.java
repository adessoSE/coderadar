package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

public interface CreateAnalyzerConfigurationPort {

  /**
   * Creates a new analyzer configuration.
   *
   * @param configuration The configuration to save
   * @param projectId The project id.
   * @return The id of the saved configuration.
   */
  Long create(AnalyzerConfiguration configuration, long projectId);
}
