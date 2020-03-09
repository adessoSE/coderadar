package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteAnalyzerConfigurationAdapter implements DeleteAnalyzerConfigurationPort {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository;

  public DeleteAnalyzerConfigurationAdapter(
      AnalyzerConfigurationRepository analyzerConfigurationRepository) {
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
  }

  @Override
  public void deleteAnalyzerConfiguration(long id) {
    if (!analyzerConfigurationRepository.existsById(id)) {
      throw new AnalyzerConfigurationNotFoundException(id);
    }
    analyzerConfigurationRepository.deleteById(id);
  }
}
