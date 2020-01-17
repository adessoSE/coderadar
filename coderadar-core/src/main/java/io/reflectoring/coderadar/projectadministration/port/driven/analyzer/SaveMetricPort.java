package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import java.util.List;

public interface SaveMetricPort {

  /**
   * Saves metric values in the DB.
   *
   * @param metricValues The metric values to save.
   */
  void saveMetricValues(List<MetricValue> metricValues);
}
