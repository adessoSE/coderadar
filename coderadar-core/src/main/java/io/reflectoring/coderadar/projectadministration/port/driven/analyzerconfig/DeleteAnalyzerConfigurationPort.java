package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

public interface DeleteAnalyzerConfigurationPort {

  /**
   * Deletes an analyzers configuration given its id.
   *
   * @param id The id of the configuration.
   */
  void deleteAnalyzerConfiguration(long id);
}
