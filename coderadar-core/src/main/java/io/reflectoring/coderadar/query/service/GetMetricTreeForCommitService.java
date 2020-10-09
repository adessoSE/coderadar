package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.port.driven.GetMetricTreeForCommitPort;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitCommand;
import io.reflectoring.coderadar.query.port.driver.metrictree.GetMetricTreeForCommitUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMetricTreeForCommitService implements GetMetricTreeForCommitUseCase {
  private final GetMetricTreeForCommitPort getMetricTreeForCommitPort;

  @Override
  public MetricTree get(long projectId, GetMetricTreeForCommitCommand command) {
    return getMetricTreeForCommitPort.get(projectId, command);
  }
}
