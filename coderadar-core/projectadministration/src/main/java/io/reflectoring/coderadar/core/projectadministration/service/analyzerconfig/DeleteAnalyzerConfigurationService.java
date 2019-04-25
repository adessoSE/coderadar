package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
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
  public void deleteAnalyzerConfiguration(Long id) {
    port.deleteAnalyzerConfiguration(id);
  }
}
