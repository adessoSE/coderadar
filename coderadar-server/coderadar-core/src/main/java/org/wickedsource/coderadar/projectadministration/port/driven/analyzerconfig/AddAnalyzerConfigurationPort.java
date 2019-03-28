package org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig;

import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;

public interface AddAnalyzerConfigurationPort {
  Long add(AnalyzerConfiguration entity);
}