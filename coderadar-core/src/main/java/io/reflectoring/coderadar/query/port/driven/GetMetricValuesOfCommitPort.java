package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;

public interface GetMetricValuesOfCommitPort {
  MetricValueForCommit[] get(GetMetricsForCommitCommand command, Long projectId);
}
