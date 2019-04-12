package org.wickedsource.coderadar.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import org.wickedsource.coderadar.query.port.driver.GetAvailableMetricsInProjectUseCase;

import java.util.List;

@Service
public class GetAvailableMetricsInProjectService implements GetAvailableMetricsInProjectUseCase {
    private final GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort;

    @Autowired
    public GetAvailableMetricsInProjectService(GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort) {
        this.getAvailableMetricsInProjectPort = getAvailableMetricsInProjectPort;
    }

    @Override
    public List<String> get(Long projectId) {
        return getAvailableMetricsInProjectPort.get(projectId);
    }
}
