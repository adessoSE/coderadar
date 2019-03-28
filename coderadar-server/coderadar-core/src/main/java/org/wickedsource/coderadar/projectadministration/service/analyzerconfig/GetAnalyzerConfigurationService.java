package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationUseCase;

@Service
public class GetAnalyzerConfigurationService
    implements GetAnalyzerConfigurationUseCase {

  private final GetAnalyzerConfigurationPort port;

  @Autowired
  public GetAnalyzerConfigurationService(GetAnalyzerConfigurationPort port) {
    this.port = port;
  }

  @Override
  public AnalyzerConfiguration getSingleAnalyzerConfiguration(GetAnalyzerConfigurationCommand command) {
    return port.getAnalyzerConfiguration(command.getId());
  }
}
