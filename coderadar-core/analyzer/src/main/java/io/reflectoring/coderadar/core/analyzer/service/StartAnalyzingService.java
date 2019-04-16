package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartAnalyzingService implements StartAnalyzingUseCase {
    private final StartAnalyzingPort startAnalyzingPort;

    @Autowired
    public StartAnalyzingService(StartAnalyzingPort startAnalyzingPort) {
        this.startAnalyzingPort = startAnalyzingPort;
    }

    @Override
    public void start(StartAnalyzingCommand command) {
        startAnalyzingPort.start(command);
    }
}
