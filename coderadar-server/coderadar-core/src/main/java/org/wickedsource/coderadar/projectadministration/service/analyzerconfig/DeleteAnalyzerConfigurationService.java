package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.DeleteAnalyzerConfigurationUseCase;

@Service
public class DeleteAnalyzerConfigurationService implements DeleteAnalyzerConfigurationUseCase {

    private final DeleteAnalyzerConfigurationPort port;

    @Autowired
    public DeleteAnalyzerConfigurationService(DeleteAnalyzerConfigurationPort port) {
        this.port = port;
    }
}
