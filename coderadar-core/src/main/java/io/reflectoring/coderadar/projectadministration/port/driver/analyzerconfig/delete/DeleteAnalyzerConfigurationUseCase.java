package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete;

public interface DeleteAnalyzerConfigurationUseCase {

  /**
   * Deletes an analyzers configuration given its id.
   *
   * @param id The id of the configuration.
   */
  void deleteAnalyzerConfiguration(long id, long projectId);
}
