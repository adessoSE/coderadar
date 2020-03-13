package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.port.driven.GetMetricTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricTreeForCommitUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.stereotype.Service;

@Service
public class GetMetricTreeForCommitService implements GetMetricTreeForCommitUseCase {
  private final GetMetricTreeForCommitPort getMetricTreeForCommitPort;

  public GetMetricTreeForCommitService(GetMetricTreeForCommitPort getMetricTreeForCommitPort) {
    this.getMetricTreeForCommitPort = getMetricTreeForCommitPort;
  }

  @Override
  public MetricTree get(GetMetricsForCommitCommand command, long projectId) {
    return getMetricTreeForCommitPort.get(command, projectId);
  }
}
