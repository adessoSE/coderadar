package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.CreateAnalyzerConfigurationService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateAnalyzerConfigurationServiceTest {
  private CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort =
      mock(CreateAnalyzerConfigurationPort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);
  private UpdateProjectPort updateProjectPort = mock(UpdateProjectPort.class);

  @Test
  void returnsNewAnalyzerConfigurationId() {
    CreateAnalyzerConfigurationService testSubject =
        new CreateAnalyzerConfigurationService(
            createAnalyzerConfigurationPort, updateProjectPort, getProjectPort);

    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    Mockito.when(createAnalyzerConfigurationPort.create(any())).thenReturn(1L);
    Mockito.when(getProjectPort.get(1L)).thenReturn(Optional.of(new Project()));

    Long analyzerConfigurationId = testSubject.create(command, 1L);

    Assertions.assertEquals(1L, analyzerConfigurationId.longValue());
  }
}
