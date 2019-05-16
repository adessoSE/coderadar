package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;

public interface UpdateAnalyzerConfigurationUseCase {
  void update(UpdateAnalyzerConfigurationCommand command, Long analyzerId) throws AnalyzerConfigurationNotFoundException;
}
