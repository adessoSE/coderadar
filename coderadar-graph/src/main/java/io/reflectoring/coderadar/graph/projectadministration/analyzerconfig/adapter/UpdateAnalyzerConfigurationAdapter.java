package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.AnalyzerConfigurationMapper;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnalyzerConfigurationAdapter implements UpdateAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;
  private final AnalyzerConfigurationMapper analyzerConfigurationMapper =
      new AnalyzerConfigurationMapper();

  public UpdateAnalyzerConfigurationAdapter(
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Override
  public void update(AnalyzerConfiguration configuration) {
    AnalyzerConfigurationEntity analyzerConfigurationEntity =
        analyzerConfigurationRepository
            .findById(configuration.getId())
            .orElseThrow(() -> new AnalyzerConfigurationNotFoundException(configuration.getId()));

    analyzerConfigurationEntity.setAnalyzerName(configuration.getAnalyzerName());
    analyzerConfigurationEntity.setEnabled(configuration.isEnabled());
    if (configuration.getAnalyzerConfigurationFile() != null) {
      analyzerConfigurationEntity.setAnalyzerConfigurationFile(
          analyzerConfigurationMapper.mapConfigurationFileDomainObject(
              configuration.getAnalyzerConfigurationFile()));
    }
    analyzerConfigurationRepository.save(analyzerConfigurationEntity);
  }
}
