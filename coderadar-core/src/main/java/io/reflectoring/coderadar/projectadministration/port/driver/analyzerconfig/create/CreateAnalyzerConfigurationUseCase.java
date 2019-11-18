package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

public interface CreateAnalyzerConfigurationUseCase {

  /**
   * Creates a new analyzer configuration.
   *
   * @param command Parameters for the new configuration.
   * @param projectId The project id.
   * @return The id of the new configuration.
   */
  Long create(CreateAnalyzerConfigurationCommand command, Long projectId);
}
