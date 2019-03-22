package org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface ListSingleAnalyzerConfigurationPort {
    AnalyzerConfiguration listSingleAnalyzerConfiguration(Long projectId, Long analyzerConfigurationId);
}
