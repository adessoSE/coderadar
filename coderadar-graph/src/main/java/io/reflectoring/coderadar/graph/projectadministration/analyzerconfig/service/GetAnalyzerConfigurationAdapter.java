package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzerConfigurationAdapter implements GetAnalyzerConfigurationPort {
  private final GetAnalyzerConfigurationRepository getAnalyzerConfigurationRepository;

  @Autowired
  public GetAnalyzerConfigurationAdapter(
      GetAnalyzerConfigurationRepository getAnalyzerConfigurationRepository) {
    this.getAnalyzerConfigurationRepository = getAnalyzerConfigurationRepository;
  }

  @Override
  public Optional<AnalyzerConfiguration> getAnalyzerConfiguration(Long id) {
    return getAnalyzerConfigurationRepository.findById(id);
  }
}
