package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAnalyzerConfigurationAdapter implements CreateAnalyzerConfigurationPort {
  private final CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @Autowired
  public CreateAnalyzerConfigurationAdapter(
      CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository) {
    this.createAnalyzerConfigurationRepository = createAnalyzerConfigurationRepository;
  }

  @Override
  public Long create(AnalyzerConfiguration entity) {
    return createAnalyzerConfigurationRepository.save(entity).getId();
  }
}
