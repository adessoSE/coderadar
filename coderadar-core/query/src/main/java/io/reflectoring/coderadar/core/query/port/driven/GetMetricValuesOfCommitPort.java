package io.reflectoring.coderadar.core.query.port.driven;

import io.reflectoring.coderadar.core.analyzer.domain.MetricValueDTO;

import java.util.List;

public interface GetMetricValuesOfCommitPort {
  List<MetricValueDTO> get(String commitHash);
}
