package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;

public interface GetMetricTreeForCommitPort {
  MetricTree get(GetMetricsForCommitCommand command, long projectId);
}
