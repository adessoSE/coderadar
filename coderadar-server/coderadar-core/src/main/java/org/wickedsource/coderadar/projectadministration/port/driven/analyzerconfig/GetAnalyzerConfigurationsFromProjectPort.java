package org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectPort {
    List<AnalyzerConfiguration> get(Long id);
}
