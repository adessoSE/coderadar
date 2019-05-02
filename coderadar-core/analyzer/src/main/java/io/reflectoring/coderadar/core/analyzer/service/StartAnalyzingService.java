package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.core.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;

  @Autowired
  public StartAnalyzingService(StartAnalyzingPort startAnalyzingPort) {
    this.startAnalyzingPort = startAnalyzingPort;
  }

  @Override
  public Long start(StartAnalyzingCommand command) {
    AnalyzingJob analyzingJob = new AnalyzingJob();
    analyzingJob.setFromDate(command.getFrom());
    analyzingJob.setRescan(command.getRescan());
    analyzingJob.setActive(true);
    return startAnalyzingPort.start(command.getProjectId(), analyzingJob);
  }
}
