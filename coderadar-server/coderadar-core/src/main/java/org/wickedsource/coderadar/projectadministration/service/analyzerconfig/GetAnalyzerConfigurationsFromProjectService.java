package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationsFromProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationsFromProjectUseCase;

import java.util.List;

@Service
public class GetAnalyzerConfigurationsFromProjectService implements GetAnalyzerConfigurationsFromProjectUseCase {
    private final GetAnalyzerConfigurationsFromProjectPort port;

    @Autowired
    public GetAnalyzerConfigurationsFromProjectService(GetAnalyzerConfigurationsFromProjectPort port) {
        this.port = port;
    }

    @Override
    public List<AnalyzerConfiguration> get(GetAnalyzerConfigurationsFromProjectCommand command) {
        return port.get(command.getId());
    }
}
