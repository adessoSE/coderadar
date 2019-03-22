package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.AddAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationUseCase;

@Service
public class AddAnalyzerConfigurationService implements AddAnalyzerConfigurationUseCase {

    private final AddAnalyzerConfigurationPort port;

    @Autowired
    public AddAnalyzerConfigurationService(AddAnalyzerConfigurationPort port) {
        this.port = port;
    }
}
