package org.wickedsource.coderadar.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.rest.dates.series.Series;
import org.wickedsource.coderadar.query.port.driven.GetHistoryOfMetricPort;
import org.wickedsource.coderadar.query.port.driver.GetHistoryOfMetricCommand;
import org.wickedsource.coderadar.query.port.driver.GetHistoryOfMetricUseCase;

@Service
public class GetHistoryOfMetricService implements GetHistoryOfMetricUseCase {
    private final GetHistoryOfMetricPort getHistoryOfMetricPort;

    @Autowired
    public GetHistoryOfMetricService(GetHistoryOfMetricPort getHistoryOfMetricPort) {
        this.getHistoryOfMetricPort = getHistoryOfMetricPort;
    }

    @Override
    public Series get(GetHistoryOfMetricCommand command) {
        return getHistoryOfMetricPort.get(command);
    }
}
