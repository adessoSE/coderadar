package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.service.ListAnalyzersService;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.CreateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.CreateAnalyzerConfigurationService;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.ListAnalyzerConfigurationsService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateAnalyzerConfigurationServiceTest {

  @Mock private CreateAnalyzerConfigurationPort createConfigurationPortMock;

  private CreateAnalyzerConfigurationService testSubject;

  @Mock private ListAnalyzerConfigurationsService listAnalyzerConfigurationsFromProjectServiceMock;

  @Mock private ListAnalyzersService listAnalyzerServiceMock;

  @Mock private GetProjectPort getProjectPort;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new CreateAnalyzerConfigurationService(
            createConfigurationPortMock,
            listAnalyzerServiceMock,
            listAnalyzerConfigurationsFromProjectServiceMock,
            getProjectPort);
  }

  @Test
  void returnsNewAnalyzerConfigurationId() {
    // given
    long projectId = 1L;
    String analyzerName = "analyzer";
    boolean analyzerEnabled = true;

    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand(analyzerName, analyzerEnabled);

    AnalyzerConfiguration expectedConfiguration =
        new AnalyzerConfiguration().setAnalyzerName(analyzerName).setEnabled(analyzerEnabled);

    when(createConfigurationPortMock.create(expectedConfiguration, projectId)).thenReturn(1L);
    when(listAnalyzerServiceMock.listAvailableAnalyzers())
        .thenReturn(Collections.singletonList(analyzerName));
    when(getProjectPort.existsById(anyLong())).thenReturn(true);

    // when
    Long analyzerConfigurationId = testSubject.create(command, 1L);

    // then
    assertThat(analyzerConfigurationId).isEqualTo(1L);

    verify(createConfigurationPortMock).create(expectedConfiguration, projectId);
  }
}
