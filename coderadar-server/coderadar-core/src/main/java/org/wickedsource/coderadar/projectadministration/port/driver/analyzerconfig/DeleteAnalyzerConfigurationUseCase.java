package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface DeleteAnalyzerConfigurationUseCase {
  void deleteAnalyzerConfiguration(AnalyzerConfiguration entity);
  void deleteAnalyzerConfiguration(Long id);
}
