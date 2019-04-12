package org.wickedsource.coderadar.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.port.driven.StopAnalyzingPort;
import org.wickedsource.coderadar.analyzer.port.driver.StopAnalyzingUseCase;

@Service
public class StopAnalyzingService implements StopAnalyzingUseCase {
    private final StopAnalyzingPort stopAnalyzingPort;

    @Autowired
    public StopAnalyzingService(StopAnalyzingPort stopAnalyzingPort) {
        this.stopAnalyzingPort = stopAnalyzingPort;
    }

    @Override
    public void stop(Long projectId) {
        stopAnalyzingPort.stop(projectId);
    }
}
