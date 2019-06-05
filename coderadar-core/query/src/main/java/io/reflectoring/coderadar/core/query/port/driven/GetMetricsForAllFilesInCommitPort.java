package io.reflectoring.coderadar.core.query.port.driven;

import io.reflectoring.coderadar.core.projectadministration.domain.analyzer.GroupedMetricValueDTO;
import io.reflectoring.coderadar.core.query.port.driver.GetMetricsForAllFilesInCommitCommand;
import java.util.List;

public interface GetMetricsForAllFilesInCommitPort {
  List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command);
}
