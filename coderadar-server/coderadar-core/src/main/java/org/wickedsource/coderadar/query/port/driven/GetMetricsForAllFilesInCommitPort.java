package org.wickedsource.coderadar.query.port.driven;

import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO;
import org.wickedsource.coderadar.query.port.driver.GetMetricsForAllFilesInCommitCommand;

import java.util.List;

public interface GetMetricsForAllFilesInCommitPort {
    List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command);
}
