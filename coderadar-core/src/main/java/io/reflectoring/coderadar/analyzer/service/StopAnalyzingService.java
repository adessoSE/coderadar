package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: Unused
@Service
public class StopAnalyzingService implements StopAnalyzingUseCase {
  private final StopAnalyzingPort stopAnalyzingPort;

  @Autowired
  public StopAnalyzingService(StopAnalyzingPort stopAnalyzingPort) {
    this.stopAnalyzingPort = stopAnalyzingPort;
  }

  @Override
  public void stop(Long projectId) {
    stopAnalyzingPort.stop(projectId);
  }
}
