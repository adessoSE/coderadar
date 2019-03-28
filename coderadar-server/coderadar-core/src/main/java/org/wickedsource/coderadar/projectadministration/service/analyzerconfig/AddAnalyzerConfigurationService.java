package org.wickedsource.coderadar.projectadministration.service.analyzerconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.port.driven.analyzerconfig.AddAnalyzerConfigurationPort;
import org.wickedsource.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationUseCase;

@Service
public class AddAnalyzerConfigurationService implements AddAnalyzerConfigurationUseCase {

    private final AddAnalyzerConfigurationPort addAnalyzerConfigurationPort;
    private final GetProjectPort getProjectPort;

    @Autowired
    public AddAnalyzerConfigurationService(AddAnalyzerConfigurationPort addAnalyzerConfigurationPort, GetProjectPort getProjectPort) {
        this.addAnalyzerConfigurationPort = addAnalyzerConfigurationPort;
        this.getProjectPort = getProjectPort;
    }

    @Override
    public Long add(AddAnalyzerConfigurationCommand command) {
        AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
        analyzerConfiguration.setEnabled(command.getEnabled());
        analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
        analyzerConfiguration.setProject(getProjectPort.get(command.getProjectId()));
        return addAnalyzerConfigurationPort.add(analyzerConfiguration);
    }
}
