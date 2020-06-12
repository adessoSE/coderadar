package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import java.util.List;
import java.util.Map;

public interface SaveMetricPort {

  /**
   * Saves metric values in the DB.
   *
   * @param metricValues The metric values to save.
   */
  void saveMetricValues(List<MetricValue> metricValues);

  /**
   * @param projectId The project id.
   * @param branch The branch name.
   * @return A map of File ids and their corresponding metrics for the head commit of the given
   *     branch. (Does not return findings).
   */
  Map<Long, List<MetricValue>> getMetricsForFiles(long projectId, String branch);
}
