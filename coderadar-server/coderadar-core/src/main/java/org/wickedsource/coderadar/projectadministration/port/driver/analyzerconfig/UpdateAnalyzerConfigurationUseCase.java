package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface UpdateAnalyzerConfigurationUseCase {
  AnalyzerConfiguration updateAnalyzerConfiguration(UpdateAnalyzerConfigurationCommand command);
}
