package org.wickedsource.coderadar.query.port.driver;

import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;

import java.util.List;

public interface GetMetricValuesOfCommitUseCase {
    List<MetricValueDTO> get(Commit commit);

    List<MetricValueDTO> get(String commitHash);
}
