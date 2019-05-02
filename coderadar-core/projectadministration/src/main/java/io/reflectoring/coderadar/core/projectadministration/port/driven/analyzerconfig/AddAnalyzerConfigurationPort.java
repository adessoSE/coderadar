package io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;

public interface AddAnalyzerConfigurationPort {
  Long add(Long projectId, AnalyzerConfiguration entity);
}
