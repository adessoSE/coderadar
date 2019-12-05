package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import java.util.Collection;

public interface ListAnalyzerConfigurationsPort {

  /**
   * Retrieves all analyzer configurations in a project.
   *
   * @param projectId The id of the project.
   * @return The analyzer configurations.
   */
  Collection<AnalyzerConfiguration> get(Long projectId);
}
