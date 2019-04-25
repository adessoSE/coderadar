package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAnalyzerConfigurationService implements CreateAnalyzerConfigurationUseCase {

  private final CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort;
  private final GetProjectPort getProjectPort;

  @Autowired
  public CreateAnalyzerConfigurationService(
      CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort,
      GetProjectPort getProjectPort) {
    this.createAnalyzerConfigurationPort = createAnalyzerConfigurationPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public Long create(CreateAnalyzerConfigurationCommand command, Long projectId) {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setEnabled(command.getEnabled());
    analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
    analyzerConfiguration.setProject(getProjectPort.get(projectId));
    return createAnalyzerConfigurationPort.create(analyzerConfiguration);
  }
}
