package io.reflectoring.coderadar.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAnalyzerConfigurationService implements DeleteAnalyzerConfigurationUseCase {

  private final DeleteAnalyzerConfigurationPort port;

  @Autowired
  public DeleteAnalyzerConfigurationService(DeleteAnalyzerConfigurationPort port) {
    this.port = port;
  }

  @Override
  public void deleteAnalyzerConfiguration(Long id) throws AnalyzerConfigurationNotFoundException {
    port.deleteAnalyzerConfiguration(id);
  }
}
