package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnalyzerConfigurationAdapter implements UpdateAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  @Autowired
  public UpdateAnalyzerConfigurationAdapter(
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Override
  public void update(AnalyzerConfiguration configuration)
      throws AnalyzerConfigurationNotFoundException {
    Optional<AnalyzerConfigurationEntity> peristedAnalyzerConfiguration =
        analyzerConfigurationRepository.findById(configuration.getId());

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
      analyzerConfigurationRepository.save(peristedAnalyzerConfiguration.get());
    } else {
      throw new AnalyzerConfigurationNotFoundException(configuration.getId());
    }
  }
}
