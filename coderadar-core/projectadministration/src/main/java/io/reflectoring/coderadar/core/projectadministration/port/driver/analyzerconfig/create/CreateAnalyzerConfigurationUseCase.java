package io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;

public interface CreateAnalyzerConfigurationUseCase {
  Long create(CreateAnalyzerConfigurationCommand command, Long projectId) throws ProjectNotFoundException;
}
