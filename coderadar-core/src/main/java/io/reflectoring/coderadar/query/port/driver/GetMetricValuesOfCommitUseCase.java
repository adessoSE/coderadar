package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.MetricValueForCommit;

import java.util.List;

public interface GetMetricValuesOfCommitUseCase {
  List<MetricValueForCommit> get(GetMetricsForCommitCommand command, Long projectId);
}
