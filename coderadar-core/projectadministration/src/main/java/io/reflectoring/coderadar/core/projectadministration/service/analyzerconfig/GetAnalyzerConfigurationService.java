package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GetAnalyzerConfigurationService")
public class GetAnalyzerConfigurationService implements GetAnalyzerConfigurationUseCase {

  private final GetAnalyzerConfigurationPort port;

  @Autowired
  public GetAnalyzerConfigurationService(GetAnalyzerConfigurationPort port) {
    this.port = port;
  }

  @Override
  public GetAnalyzerConfigurationResponse getSingleAnalyzerConfiguration(Long id) {
    Optional<AnalyzerConfiguration> analyzerConfiguration = port.getAnalyzerConfiguration(id);

    if (analyzerConfiguration.isPresent()) {
      return new GetAnalyzerConfigurationResponse(
          id,
          analyzerConfiguration.get().getAnalyzerName(),
          analyzerConfiguration.get().getEnabled());
    } else {
      throw new AnalyzerConfigurationNotFoundException();
    }
  }
}
