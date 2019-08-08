package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.port.driven.GetMetricsForAllFilesInCommitPort;
import io.reflectoring.coderadar.query.port.driven.MetricTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForAllFilesInCommitUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.stereotype.Service;

@Service
public class GetMetricsForAllFilesInCommitService implements GetMetricsForAllFilesInCommitUseCase {
  private final GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort;

  public GetMetricsForAllFilesInCommitService(
      GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort) {
    this.getMetricsForAllFilesInCommitPort = getMetricsForAllFilesInCommitPort;
  }

  @Override
  public MetricTree get(GetMetricsForCommitCommand command, Long projectId) {
    return getMetricsForAllFilesInCommitPort.get(command, projectId);
  }
}
