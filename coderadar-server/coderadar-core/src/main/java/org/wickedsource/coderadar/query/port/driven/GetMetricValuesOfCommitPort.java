package org.wickedsource.coderadar.query.port.driven;

import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;

import java.util.List;

public interface GetMetricValuesOfCommitPort {
    List<MetricValueDTO> get(String commitHash);
}
