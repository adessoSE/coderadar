package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateAnalyzerConfigurationAdapter implements UpdateAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;

  @Override
  public void update(long configurationId, UpdateAnalyzerConfigurationCommand command) {
    AnalyzerConfigurationEntity analyzerConfigurationEntity =
        analyzerConfigurationRepository
            .findById(configurationId)
            .orElseThrow(() -> new AnalyzerConfigurationNotFoundException(configurationId));

    analyzerConfigurationEntity.setAnalyzerName(command.getAnalyzerName());
    analyzerConfigurationEntity.setEnabled(command.isEnabled());
    analyzerConfigurationRepository.save(analyzerConfigurationEntity);
  }
}
