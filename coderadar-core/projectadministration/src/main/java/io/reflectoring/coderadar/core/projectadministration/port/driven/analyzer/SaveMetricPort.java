package io.reflectoring.coderadar.core.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.core.projectadministration.domain.MetricValue;

public interface SaveMetricPort {
  void saveMetricValue(MetricValue metricValue);
}
