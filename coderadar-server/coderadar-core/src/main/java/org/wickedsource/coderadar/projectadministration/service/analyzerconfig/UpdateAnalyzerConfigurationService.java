package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationUseCase;

@Service
public class UpdateAnalyzerConfigurationService implements UpdateAnalyzerConfigurationUseCase {

  private final GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;
  private final UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort;

  @Autowired
  public UpdateAnalyzerConfigurationService(UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort, GetAnalyzerConfigurationPort getAnalyzerConfigurationPort) {
    this.updateAnalyzerConfigurationPort = updateAnalyzerConfigurationPort;
    this.getAnalyzerConfigurationPort = getAnalyzerConfigurationPort;
  }

  @Override
  public void update(
      UpdateAnalyzerConfigurationCommand command) {
    AnalyzerConfiguration analyzerConfiguration =
            getAnalyzerConfigurationPort.getAnalyzerConfiguration(command.getId());
    analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
    analyzerConfiguration.setEnabled(command.getEnabled());
    updateAnalyzerConfigurationPort.update(analyzerConfiguration);
  }
}
