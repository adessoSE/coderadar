package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;

import java.util.List;

public interface SaveMetricPort {
  void saveMetricValues(List<MetricValue> metricValues, Long projectId);
}
