package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("DeleteAnalyzerConfigurationService")
public class DeleteAnalyzerConfigurationService implements DeleteAnalyzerConfigurationUseCase {

  private final DeleteAnalyzerConfigurationPort port;
  private final GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;

  @Autowired
  public DeleteAnalyzerConfigurationService(
      @Qualifier("DeleteAnalyzerConfigurationServiceNeo4j") DeleteAnalyzerConfigurationPort port,
      GetAnalyzerConfigurationPort getAnalyzerConfigurationPort) {
    this.port = port;
    this.getAnalyzerConfigurationPort = getAnalyzerConfigurationPort;
  }

  @Override
  public void deleteAnalyzerConfiguration(Long id) throws AnalyzerConfigurationNotFoundException {
    if (getAnalyzerConfigurationPort.getAnalyzerConfiguration(id).isPresent()) {
      port.deleteAnalyzerConfiguration(id);
    } else {
      throw new AnalyzerConfigurationNotFoundException(id);
    }
  }
}
