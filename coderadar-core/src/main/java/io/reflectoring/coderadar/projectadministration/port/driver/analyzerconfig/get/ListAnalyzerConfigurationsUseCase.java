package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;

import java.util.List;

public interface ListAnalyzerConfigurationsUseCase {

  /**
   * Retrieves all analyzer configurations in a project.
   *
   * @param projectId The id of the project.
   * @return The analyzer configurations.
   */
  List<AnalyzerConfiguration> get(Long projectId);
}
