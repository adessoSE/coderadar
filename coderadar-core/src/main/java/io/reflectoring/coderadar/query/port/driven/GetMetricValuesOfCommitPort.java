package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.analyzer.domain.MetricValueDTO;
import java.util.List;

public interface GetMetricValuesOfCommitPort {
  List<MetricValueDTO> get(String commitHash);
}
