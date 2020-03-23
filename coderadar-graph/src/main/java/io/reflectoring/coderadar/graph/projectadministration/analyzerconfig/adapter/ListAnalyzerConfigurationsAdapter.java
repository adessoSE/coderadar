package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.ListAnalyzerConfigurationsPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListAnalyzerConfigurationsAdapter implements ListAnalyzerConfigurationsPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  public ListAnalyzerConfigurationsAdapter(
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Override
  public List<AnalyzerConfiguration> listAnalyzerConfigurations(long projectId) {
    return analyzerConfigurationMapper.mapNodeEntities(
        analyzerConfigurationRepository.findByProjectId(projectId));
  }
}
