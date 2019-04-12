package org.wickedsource.coderadar.analyzer.port.driven;

import org.wickedsource.coderadar.analyzer.port.driver.StartAnalyzingCommand;

public interface StartAnalyzingPort {
    void start(StartAnalyzingCommand command);
}
