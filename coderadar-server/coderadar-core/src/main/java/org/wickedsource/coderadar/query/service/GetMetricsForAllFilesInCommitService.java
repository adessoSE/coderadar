package org.wickedsource.coderadar.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO;
import org.wickedsource.coderadar.query.port.driven.GetMetricsForAllFilesInCommitPort;
import org.wickedsource.coderadar.query.port.driver.GetMetricsForAllFilesInCommitCommand;
import org.wickedsource.coderadar.query.port.driver.GetMetricsForAllFilesInCommitUseCase;

import java.util.List;

@Service
public class GetMetricsForAllFilesInCommitService implements GetMetricsForAllFilesInCommitUseCase {
    private final GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort;

    @Autowired
    public GetMetricsForAllFilesInCommitService(GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort) {
        this.getMetricsForAllFilesInCommitPort = getMetricsForAllFilesInCommitPort;
    }

    @Override
    public List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command) {
        return getMetricsForAllFilesInCommitPort.get(command);
    }
}
