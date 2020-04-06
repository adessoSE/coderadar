package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitCommand;
import java.util.List;

public interface GetMetricValuesOfCommitPort {

  /**
   * @param command The command containing the commit hash and the metrics we are interested in.
   * @param projectId The id of the project.
   * @return Aggregated metrics for all files in the given commit.
   */
  List<MetricValueForCommit> get(GetMetricValuesOfCommitCommand command, long projectId);
}
