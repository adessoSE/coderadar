package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("StartAnalyzingService")
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;

  @Autowired
  public StartAnalyzingService(
      @Qualifier("StartAnalyzingServiceNeo4j") StartAnalyzingPort startAnalyzingPort) {
    this.startAnalyzingPort = startAnalyzingPort;
  }

  @Override
  public Long start(StartAnalyzingCommand command) {
    return startAnalyzingPort.start(command);
  }
}
