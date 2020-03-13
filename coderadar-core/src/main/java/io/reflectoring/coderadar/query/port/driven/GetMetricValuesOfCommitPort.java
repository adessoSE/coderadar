package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import java.util.List;

public interface GetMetricValuesOfCommitPort {
  List<MetricValueForCommit> get(GetMetricsForCommitCommand command, long projectId);
}
