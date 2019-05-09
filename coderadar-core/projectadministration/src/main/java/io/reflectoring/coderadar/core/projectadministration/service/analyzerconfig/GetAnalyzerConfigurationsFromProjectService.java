package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationsFromProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GetAnalyzerConfigurationsFromProjectService")
public class GetAnalyzerConfigurationsFromProjectService
    implements GetAnalyzerConfigurationsFromProjectUseCase {
  private final GetAnalyzerConfigurationsFromProjectPort port;

  @Autowired
  public GetAnalyzerConfigurationsFromProjectService(
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
