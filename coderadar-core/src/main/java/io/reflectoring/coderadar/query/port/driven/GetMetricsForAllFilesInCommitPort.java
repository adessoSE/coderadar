package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.analyzer.domain.GroupedMetricValueDTO;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForAllFilesInCommitCommand;

import java.util.List;

public interface GetMetricsForAllFilesInCommitPort {
  List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command);
}
