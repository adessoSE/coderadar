package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.UpdateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnalyzerConfigurationAdapter implements UpdateAnalyzerConfigurationPort {
  private final GetAnalyzerConfigurationsFromProjectRepository
      getAnalyzerConfigurationsFromProjectRepository;
  private final UpdateAnalyzerConfigurationRepository updateAnalyzerConfigurationRepository;

  @Autowired
  public UpdateAnalyzerConfigurationAdapter(
      GetAnalyzerConfigurationsFromProjectRepository getAnalyzerConfigurationsFromProjectRepository,
      UpdateAnalyzerConfigurationRepository updateAnalyzerConfigurationRepository) {
    this.getAnalyzerConfigurationsFromProjectRepository =
        getAnalyzerConfigurationsFromProjectRepository;
    this.updateAnalyzerConfigurationRepository = updateAnalyzerConfigurationRepository;
  }

  @Override
  public void update(AnalyzerConfiguration entity) {
    Optional<AnalyzerConfiguration> peristedAnalyzerConfiguration =
        getAnalyzerConfigurationsFromProjectRepository.findById(entity.getId());

    if (peristedAnalyzerConfiguration.isPresent()) {
      updateAnalyzerConfigurationRepository.save(entity);
    } else {
      throw new AnalyzerConfigurationNotFoundException(entity.getId());
    }
  }
}
