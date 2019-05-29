package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.UpdateAnalyzerConfigurationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;

class UpdateAnalyzerConfigurationServiceTest {
  private UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort =
      mock(UpdateAnalyzerConfigurationPort.class);
  private GetAnalyzerConfigurationPort getAnalyzerConfigurationPort =
      mock(GetAnalyzerConfigurationPort.class);

  @Test
  void updateAnalyzerConfigurationWithIdOne() {
    UpdateAnalyzerConfigurationService testSubject =
        new UpdateAnalyzerConfigurationService(
            updateAnalyzerConfigurationPort, getAnalyzerConfigurationPort);

    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("new analyzer name", true);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setId(1L);
    analyzerConfiguration.setAnalyzerName("new analyzer name");
    analyzerConfiguration.setEnabled(true);

    Mockito.when(getAnalyzerConfigurationPort.getAnalyzerConfiguration(1L))
        .thenReturn(Optional.of(analyzerConfiguration));

    testSubject.update(command, 1L);

    Mockito.verify(updateAnalyzerConfigurationPort, Mockito.times(1)).update(analyzerConfiguration);
  }
}
