package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.ListAnalyzerConfigurationsPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.ListAnalyzerConfigurationsUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListAnalyzerConfigurationsService implements ListAnalyzerConfigurationsUseCase {
  private final ListAnalyzerConfigurationsPort port;

  public ListAnalyzerConfigurationsService(ListAnalyzerConfigurationsPort port) {
    this.port = port;
  }

  @Override
  public List<AnalyzerConfiguration> get(Long projectId) {
    return port.get(projectId);
  }
}
