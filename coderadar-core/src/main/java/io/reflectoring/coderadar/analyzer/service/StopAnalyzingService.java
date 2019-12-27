package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.SetAnalyzingStatusPort;
import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StopAnalyzingService implements StopAnalyzingUseCase {
  private final SetAnalyzingStatusPort setAnalyzingStatusPort;

  @Autowired
  public StopAnalyzingService(SetAnalyzingStatusPort setAnalyzingStatusPort) {
    this.setAnalyzingStatusPort = setAnalyzingStatusPort;
  }

  @Override
  public void stop(Long projectId) {
    setAnalyzingStatusPort.setStatus(projectId, false);
  }
}
