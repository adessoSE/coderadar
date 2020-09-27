package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;

public interface UpdateAnalyzerConfigurationPort {

  /**
   * Updates a single analyzer configuration
   *
   * @param configurationId The id of the analyzer configuration.
   * @param command The command containing the updated data.
   */
  void update(long configurationId, UpdateAnalyzerConfigurationCommand command);
}
