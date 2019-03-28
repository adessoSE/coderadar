package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.ListSingleAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.ListSingleAnalyzerConfigurationCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.ListSingleAnalyzerConfigurationUseCase;

@Service
public class ListSingleAnalyzerConfigurationService
    implements ListSingleAnalyzerConfigurationUseCase {

  private final ListSingleAnalyzerConfigurationPort port;

  @Autowired
  public ListSingleAnalyzerConfigurationService(ListSingleAnalyzerConfigurationPort port) {
    this.port = port;
  }

  @Override
  public AnalyzerConfiguration getSingleAnalyzerConfiguration(ListSingleAnalyzerConfigurationCommand command) {
    // TODO
    return null;
  }
}
