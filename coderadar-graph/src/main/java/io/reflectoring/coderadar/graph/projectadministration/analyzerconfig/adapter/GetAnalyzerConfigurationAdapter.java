package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAnalyzerConfigurationAdapter implements GetAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  @Override
  public AnalyzerConfiguration getAnalyzerConfiguration(long id) {
    return analyzerConfigurationMapper.mapGraphObject(
        analyzerConfigurationRepository
            .findById(id, 0)
            .orElseThrow(() -> new AnalyzerConfigurationNotFoundException(id)));
  }

  @Override
  public boolean existsById(long configurationId) {
    return analyzerConfigurationRepository.existsById(configurationId);
  }
}
