package io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;

public interface CreateAnalyzerConfigurationUseCase {
  Long create(CreateAnalyzerConfigurationCommand command, Long projectId)
      throws ProjectNotFoundException;
}
