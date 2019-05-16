package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.CreateAnalyzerConfigurationService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CreateAnalyzerConfigurationServiceTest {
  @Mock private CreateAnalyzerConfigurationPort createAnalyzerConfigurationPort;
  @Mock private GetProjectPort getProjectPort;
  @InjectMocks private CreateAnalyzerConfigurationService testSubject;

  @Test
  void returnsNewAnalyzerConfigurationId() {
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
