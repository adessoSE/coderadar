package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.port.driven.GetMetricTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitCommand;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetMetricTreeForCommitService implements GetMetricTreeForCommitUseCase {
  private final GetMetricTreeForCommitPort getMetricTreeForCommitPort;

  public GetMetricTreeForCommitService(GetMetricTreeForCommitPort getMetricTreeForCommitPort) {
    this.getMetricTreeForCommitPort = getMetricTreeForCommitPort;
  }

  @Override
  public MetricTree get(GetMetricTreeForCommitCommand command, long projectId) {
    return getMetricTreeForCommitPort.get(command, projectId);
  }
}
