package io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnalyzerConfigurationService implements UpdateAnalyzerConfigurationUseCase {

    private final GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;
    private final UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort;

    @Autowired
    public UpdateAnalyzerConfigurationService(UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort, GetAnalyzerConfigurationPort getAnalyzerConfigurationPort) {
        this.updateAnalyzerConfigurationPort = updateAnalyzerConfigurationPort;
        this.getAnalyzerConfigurationPort = getAnalyzerConfigurationPort;
    }

    @Override
    public void update(
            UpdateAnalyzerConfigurationCommand command) {
        AnalyzerConfiguration analyzerConfiguration =
                getAnalyzerConfigurationPort.getAnalyzerConfiguration(command.getId());
        analyzerConfiguration.setAnalyzerName(command.getAnalyzerName());
        analyzerConfiguration.setEnabled(command.getEnabled());
        updateAnalyzerConfigurationPort.update(analyzerConfiguration);
    }
}
