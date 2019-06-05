package io.reflectoring.coderadar.core.query.port.driven;

import io.reflectoring.coderadar.core.projectadministration.domain.analyzer.MetricValueDTO;
import java.util.List;

public interface GetMetricValuesOfCommitPort {
  List<MetricValueDTO> get(String commitHash);
}
