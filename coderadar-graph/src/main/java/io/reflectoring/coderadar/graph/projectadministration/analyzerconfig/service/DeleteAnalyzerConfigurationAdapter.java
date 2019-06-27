package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAnalyzerConfigurationAdapter implements DeleteAnalyzerConfigurationPort {
  private final DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository;

  @Autowired
  public DeleteAnalyzerConfigurationAdapter(
      DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository) {
    this.deleteAnalyzerConfigurationRepository = deleteAnalyzerConfigurationRepository;
  }

  @Override
  public void deleteAnalyzerConfiguration(Long id) throws AnalyzerConfigurationNotFoundException {
    deleteAnalyzerConfigurationRepository
        .findById(id)
        .orElseThrow(() -> new AnalyzerConfigurationNotFoundException(id));
    deleteAnalyzerConfigurationRepository.deleteById(id);
  }
}
