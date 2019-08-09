package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.port.driven.MetricTree;

public interface GetMetricsForAllFilesInCommitUseCase {
  MetricTree get(GetMetricsForCommitCommand command, Long projectId);
}
