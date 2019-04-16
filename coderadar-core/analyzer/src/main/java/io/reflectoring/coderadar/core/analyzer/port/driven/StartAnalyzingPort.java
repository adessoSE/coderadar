package io.reflectoring.coderadar.core.analyzer.port.driven;


import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;

public interface StartAnalyzingPort {
    void start(StartAnalyzingCommand command);
}
