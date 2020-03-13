package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update;

public interface UpdateAnalyzerConfigurationUseCase {

  /**
   * @param command The command containing the updated configuration parameters.
   * @param configurationId The id of the configuration to update.
   * @param projectId The id of the project.
   */
  void update(UpdateAnalyzerConfigurationCommand command, long configurationId, long projectId);
}
