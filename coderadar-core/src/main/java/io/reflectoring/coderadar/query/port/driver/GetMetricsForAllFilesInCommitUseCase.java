package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.analyzer.domain.GroupedMetricValueDTO;

import java.util.List;

public interface GetMetricsForAllFilesInCommitUseCase {
  List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command);
}
