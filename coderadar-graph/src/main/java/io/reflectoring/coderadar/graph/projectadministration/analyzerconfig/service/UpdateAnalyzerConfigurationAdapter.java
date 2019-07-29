package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.UpdateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAnalyzerConfigurationAdapter implements UpdateAnalyzerConfigurationPort {
  private final GetAnalyzerConfigurationsFromProjectRepository
      getAnalyzerConfigurationsFromProjectRepository;
  private final UpdateAnalyzerConfigurationRepository updateAnalyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  @Autowired
  public UpdateAnalyzerConfigurationAdapter(
      GetAnalyzerConfigurationsFromProjectRepository getAnalyzerConfigurationsFromProjectRepository,
      UpdateAnalyzerConfigurationRepository updateAnalyzerConfigurationRepository) {
    this.getAnalyzerConfigurationsFromProjectRepository =
        getAnalyzerConfigurationsFromProjectRepository;
    this.updateAnalyzerConfigurationRepository = updateAnalyzerConfigurationRepository;
  }

  @Override
  public void update(AnalyzerConfiguration configuration)
      throws AnalyzerConfigurationNotFoundException {
    Optional<AnalyzerConfigurationEntity> peristedAnalyzerConfiguration =
        getAnalyzerConfigurationsFromProjectRepository.findById(configuration.getId());

    if (peristedAnalyzerConfiguration.isPresent()) {
      peristedAnalyzerConfiguration.get().setAnalyzerName(configuration.getAnalyzerName());
      peristedAnalyzerConfiguration.get().setEnabled(configuration.getEnabled());
      if (configuration.getAnalyzerConfigurationFile() != null) {
        peristedAnalyzerConfiguration
            .get()
            .setAnalyzerConfigurationFile(
                analyzerConfigurationMapper.mapConfigurationFileDomainObject(
                    configuration.getAnalyzerConfigurationFile()));
      }
      updateAnalyzerConfigurationRepository.save(peristedAnalyzerConfiguration.get());
    } else {
      throw new AnalyzerConfigurationNotFoundException(configuration.getId());
    }
  }
}
