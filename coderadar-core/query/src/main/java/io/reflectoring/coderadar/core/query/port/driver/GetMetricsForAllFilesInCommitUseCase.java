package io.reflectoring.coderadar.core.query.port.driver;

import io.reflectoring.coderadar.core.analyzer.domain.GroupedMetricValueDTO;

import java.util.List;

public interface GetMetricsForAllFilesInCommitUseCase {
  List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command);
}
