package io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;

public interface UpdateAnalyzerConfigurationPort {
    void update(AnalyzerConfiguration entity);
}
