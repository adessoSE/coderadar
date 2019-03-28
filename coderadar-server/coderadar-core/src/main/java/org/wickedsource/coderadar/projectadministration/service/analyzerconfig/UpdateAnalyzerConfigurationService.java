package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationUseCase;

@Service
public class UpdateAnalyzerConfigurationService implements UpdateAnalyzerConfigurationUseCase {

  private final UpdateAnalyzerConfigurationPort port;

  @Autowired
  public UpdateAnalyzerConfigurationService(UpdateAnalyzerConfigurationPort port) {
    this.port = port;
  }

  @Override
  public void update(
      UpdateAnalyzerConfigurationCommand command) {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setId(command.getId());
    analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
    analyzerConfiguration.setEnabled(command.getEnabled());
    port.update(analyzerConfiguration);
  }
}
