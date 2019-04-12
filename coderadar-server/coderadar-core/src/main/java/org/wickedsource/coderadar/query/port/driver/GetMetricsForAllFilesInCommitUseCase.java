package org.wickedsource.coderadar.query.port.driver;

import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO;

import java.util.List;

public interface GetMetricsForAllFilesInCommitUseCase {
    List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command);
}
