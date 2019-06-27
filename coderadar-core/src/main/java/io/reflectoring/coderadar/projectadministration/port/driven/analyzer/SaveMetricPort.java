package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;

public interface SaveMetricPort {
  void saveMetricValue(MetricValue metricValue);
}
