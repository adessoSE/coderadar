package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.CreateAnalyzerConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateAnalyzerConfigurationServiceTest {
  @Mock private CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort;
  @Mock private GetProjectPort getProjectPort;
  private CreateAnalyzerConfigurationService testSubject;

  @BeforeEach
  void setup() {
    testSubject =
        new CreateAnalyzerConfigurationService(createAnalyzerConfigurationPort, getProjectPort);
  }

  @Test
  void returnsNewAnalyzerConfigurationId() {
    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    Mockito.when(createAnalyzerConfigurationPort.create(analyzerConfiguration)).thenReturn(1L);

    Long analyzerConfigurationId = testSubject.create(command, 1L);

    Assertions.assertEquals(1L, analyzerConfigurationId.longValue());
  }
}
