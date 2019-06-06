package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DeleteAnalyzerConfigurationServiceNeo4j")
public class DeleteAnalyzerConfigurationService implements DeleteAnalyzerConfigurationPort {
  private final DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository;

  @Autowired
  public DeleteAnalyzerConfigurationService(
      DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository) {
    this.deleteAnalyzerConfigurationRepository = deleteAnalyzerConfigurationRepository;
  }

  @Override
  public void deleteAnalyzerConfiguration(Long id) {
    deleteAnalyzerConfigurationRepository.deleteById(id);
  }
}
