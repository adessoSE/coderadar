package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;

public interface GetMetricsForAllFilesInCommitPort {
  MetricTree get(GetMetricsForCommitCommand command, Long projectId);
}
