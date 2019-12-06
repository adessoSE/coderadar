package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzerConfigurationService implements GetAnalyzerConfigurationUseCase {

  private final GetAnalyzerConfigurationPort port;

  public GetAnalyzerConfigurationService(GetAnalyzerConfigurationPort port) {
    this.port = port;
  }

  @Override
  public GetAnalyzerConfigurationResponse getAnalyzerConfiguration(Long id) {
    AnalyzerConfiguration analyzerConfiguration = port.getAnalyzerConfiguration(id);

    return new GetAnalyzerConfigurationResponse(
        id, analyzerConfiguration.getAnalyzerName(), analyzerConfiguration.getEnabled());
  }
}
