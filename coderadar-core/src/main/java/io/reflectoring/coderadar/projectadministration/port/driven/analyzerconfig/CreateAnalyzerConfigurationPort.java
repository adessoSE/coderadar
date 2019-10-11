package io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface CreateAnalyzerConfigurationPort {
  Long create(AnalyzerConfiguration entity, Long projectId) throws ProjectNotFoundException;
}
