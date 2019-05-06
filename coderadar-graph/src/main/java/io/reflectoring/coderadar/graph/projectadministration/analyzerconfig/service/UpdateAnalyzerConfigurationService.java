package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.UpdateAnalyzerConfigurationRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnalyzerConfigurationService implements UpdateAnalyzerConfigurationPort {
  private final GetAnalyzerConfigurationsFromProjectRepository
      getAnalyzerConfigurationsFromProjectRepository;
  private final UpdateAnalyzerConfigurationRepository updateAnalyzerConfigurationRepository;

  @Autowired
  public UpdateAnalyzerConfigurationService(
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
      throw new AnalyzerConfigurationNotFoundException(
          "Can't update a non-existing analyzer configuration.");
    }
  }
}
