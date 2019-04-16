package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationsFromProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationsFromProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzerConfigurationsFromProjectService
    implements GetAnalyzerConfigurationsFromProjectUseCase {
  private final GetAnalyzerConfigurationsFromProjectPort port;

  @Autowired
  public GetAnalyzerConfigurationsFromProjectService(
      GetAnalyzerConfigurationsFromProjectPort port) {
    this.port = port;
  }

  @Override
  public List<AnalyzerConfiguration> get(GetAnalyzerConfigurationsFromProjectCommand command) {
    return port.get(command.getId());
  }
}