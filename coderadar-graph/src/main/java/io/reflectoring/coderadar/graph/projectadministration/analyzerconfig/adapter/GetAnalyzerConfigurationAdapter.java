package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzerConfigurationAdapter implements GetAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  public GetAnalyzerConfigurationAdapter(
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Override
  public AnalyzerConfiguration getAnalyzerConfiguration(Long id) {
    return analyzerConfigurationMapper.mapNodeEntity(
        analyzerConfigurationRepository
            .findById(id)
            .orElseThrow(() -> new AnalyzerConfigurationNotFoundException(id)));
  }
}
