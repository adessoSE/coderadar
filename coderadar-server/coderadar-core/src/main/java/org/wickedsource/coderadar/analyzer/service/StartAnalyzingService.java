package org.wickedsource.coderadar.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import org.wickedsource.coderadar.analyzer.port.driven.StartAnalyzingPort;
import org.wickedsource.coderadar.analyzer.port.driver.StartAnalyzingUseCase;

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
