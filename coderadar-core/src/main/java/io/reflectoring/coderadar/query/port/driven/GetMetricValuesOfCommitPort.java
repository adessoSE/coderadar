package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;

import java.util.List;

public interface GetMetricValuesOfCommitPort {
  List<MetricValue> get(String commitHash);
}
