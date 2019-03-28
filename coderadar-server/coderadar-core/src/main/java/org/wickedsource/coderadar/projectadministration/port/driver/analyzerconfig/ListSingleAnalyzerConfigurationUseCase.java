package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface ListSingleAnalyzerConfigurationUseCase {
  AnalyzerConfiguration getSingleAnalyzerConfiguration(
      ListSingleAnalyzerConfigurationCommand command);
}
