package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.MetricValueForCommit;

public interface GetMetricValuesOfCommitUseCase {
  MetricValueForCommit[] get(GetMetricsForCommitCommand command, Long projectId);
}
