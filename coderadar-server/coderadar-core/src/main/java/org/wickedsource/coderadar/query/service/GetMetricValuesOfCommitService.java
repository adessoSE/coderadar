package org.wickedsource.coderadar.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import org.wickedsource.coderadar.query.port.driver.GetMetricValuesOfCommitUseCase;

import java.util.List;

@Service
public class GetMetricValuesOfCommitService implements GetMetricValuesOfCommitUseCase {
    private final GetMetricValuesOfCommitPort getMetricValuesOfCommitPort;

    @Autowired
    public GetMetricValuesOfCommitService(GetMetricValuesOfCommitPort getMetricValuesOfCommitPort) {
        this.getMetricValuesOfCommitPort = getMetricValuesOfCommitPort;
    }

    @Override
    public List<MetricValueDTO> get(Commit commit) {
        return getMetricValuesOfCommitPort.get(commit.getName());
    }

    @Override
    public List<MetricValueDTO> get(String commitHash) {
        return getMetricValuesOfCommitPort.get(commitHash);
    }
}
