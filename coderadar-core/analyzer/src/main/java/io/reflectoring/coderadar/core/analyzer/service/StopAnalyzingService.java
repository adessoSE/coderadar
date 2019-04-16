package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StopAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
