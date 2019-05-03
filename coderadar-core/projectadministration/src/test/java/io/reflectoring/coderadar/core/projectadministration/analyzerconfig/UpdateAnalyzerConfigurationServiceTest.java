package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.UpdateAnalyzerConfigurationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UpdateAnalyzerConfigurationServiceTest {
  @Mock private UpdateAnalyzerConfigurationPort updateAnalyzerConfigurationPort;
  @Mock private GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;
  @InjectMocks private UpdateAnalyzerConfigurationService testSubject;

  @Test
  void updateAnalyzerConfigurationWithIdOne() {
    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("new analyzer name", true);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setId(1L);
    analyzerConfiguration.setAnalyzerName("new analyzer name");
    analyzerConfiguration.setEnabled(true);

    Mockito.when(getAnalyzerConfigurationPort.getAnalyzerConfiguration(1L))
        .thenReturn(analyzerConfiguration);

    testSubject.update(command, 1L);

    Mockito.verify(updateAnalyzerConfigurationPort, Mockito.times(1)).update(analyzerConfiguration);
  }
}
