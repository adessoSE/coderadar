package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAnalyzerConfigurationService implements CreateAnalyzerConfigurationPort {
  private final CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @Autowired
  public CreateAnalyzerConfigurationService(CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository) {
    this.createAnalyzerConfigurationRepository = createAnalyzerConfigurationRepository;
  }

  @Override
  public Long create(AnalyzerConfiguration entity) {
    return createAnalyzerConfigurationRepository.save(entity).getId();
  }
}
