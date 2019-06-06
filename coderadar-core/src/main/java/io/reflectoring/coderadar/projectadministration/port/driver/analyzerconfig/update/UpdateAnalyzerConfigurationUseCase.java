package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update;

import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;

public interface UpdateAnalyzerConfigurationUseCase {
  void update(UpdateAnalyzerConfigurationCommand command, Long analyzerId)
      throws AnalyzerConfigurationNotFoundException;
}
