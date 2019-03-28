package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

import java.util.List;

public interface GetAnalyzerConfigurationsFromProjectUseCase {
    List<AnalyzerConfiguration> get(GetAnalyzerConfigurationsFromProjectCommand command);
}
