package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.MetricTree;

public interface GetMetricTreeForCommitUseCase {
  MetricTree get(GetMetricsForCommitCommand command, Long projectId);
}
