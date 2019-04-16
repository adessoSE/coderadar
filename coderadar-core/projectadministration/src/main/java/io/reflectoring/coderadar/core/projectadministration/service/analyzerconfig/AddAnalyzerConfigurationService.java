package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.AddAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddAnalyzerConfigurationService implements AddAnalyzerConfigurationUseCase {

  private final AddAnalyzerConfigurationPort addAnalyzerConfigurationPort;
  private final GetProjectPort getProjectPort;

  @Autowired
  public AddAnalyzerConfigurationService(
      AddAnalyzerConfigurationPort addAnalyzerConfigurationPort, GetProjectPort getProjectPort) {
    this.addAnalyzerConfigurationPort = addAnalyzerConfigurationPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public Long add(AddAnalyzerConfigurationCommand command) {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setEnabled(command.getEnabled());
    analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
    analyzerConfiguration.setProject(getProjectPort.get(command.getProjectId()));
    return addAnalyzerConfigurationPort.add(analyzerConfiguration);
  }
}
