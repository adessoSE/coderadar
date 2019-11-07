package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;

import java.util.List;

public interface GetMetricValuesOfCommitUseCase {
  List<MetricValue> get(Commit commit);

  List<MetricValueForCommit> get(GetMetricsForCommitCommand command, Long projectId);
}
