package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.GetAnalyzingStatusPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.GetAnalyzingStatusUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("GetAnalyzingStatusService")
public class GetAnalyzingStatusService implements GetAnalyzingStatusUseCase {
  private final GetAnalyzingStatusPort getAnalyzingStatusPort;

  @Autowired
  public GetAnalyzingStatusService(
      @Qualifier("GetAnalyzingStatusServiceNeo4j") GetAnalyzingStatusPort getAnalyzingStatusPort) {
    this.getAnalyzingStatusPort = getAnalyzingStatusPort;
  }

  @Override
  public boolean get(Long projectId) {
    return getAnalyzingStatusPort.get(projectId);
  }
}
