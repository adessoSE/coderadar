package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzerConfigurationAdapter implements GetAnalyzerConfigurationPort {
  private final GetAnalyzerConfigurationRepository getAnalyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  @Autowired
  public GetAnalyzerConfigurationAdapter(
      GetAnalyzerConfigurationRepository getAnalyzerConfigurationRepository) {
    this.getAnalyzerConfigurationRepository = getAnalyzerConfigurationRepository;
  }

  @Override
  public AnalyzerConfiguration getAnalyzerConfiguration(Long id)
      throws AnalyzerConfigurationNotFoundException {
    return analyzerConfigurationMapper.mapNodeEntity(
        getAnalyzerConfigurationRepository
            .findById(id)
            .orElseThrow(() -> new AnalyzerConfigurationNotFoundException(id)));
  }
}
