package org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface GetAnalyzerConfigurationPort {
  AnalyzerConfiguration getAnalyzerConfiguration(Long id);
}
