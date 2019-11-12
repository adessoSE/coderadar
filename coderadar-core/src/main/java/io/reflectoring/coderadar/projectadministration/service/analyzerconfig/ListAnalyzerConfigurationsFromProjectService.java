package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationsFromProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListAnalyzerConfigurationsFromProjectService
    implements GetAnalyzerConfigurationsFromProjectUseCase {
  private final GetAnalyzerConfigurationsFromProjectPort port;

  public ListAnalyzerConfigurationsFromProjectService(
      GetAnalyzerConfigurationsFromProjectPort port) {
    this.port = port;
  }

  @Override
  public List<GetAnalyzerConfigurationResponse> get(Long projectId) {
    List<GetAnalyzerConfigurationResponse> configurations = new ArrayList<>();
    for (AnalyzerConfiguration analyzerConfiguration : port.get(projectId)) {
      configurations.add(
          new GetAnalyzerConfigurationResponse(
              analyzerConfiguration.getId(),
              analyzerConfiguration.getAnalyzerName(),
              analyzerConfiguration.getEnabled()));
    }
    return configurations;
  }
}
