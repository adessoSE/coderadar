package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get;

public interface GetAnalyzerConfigurationUseCase {

  /**
   * Retrieves an analyzer configuration given its id.
   *
   * @param id The id of the configuration.
   * @return The configuration.
   */
  GetAnalyzerConfigurationResponse getAnalyzerConfiguration(Long id);
}
