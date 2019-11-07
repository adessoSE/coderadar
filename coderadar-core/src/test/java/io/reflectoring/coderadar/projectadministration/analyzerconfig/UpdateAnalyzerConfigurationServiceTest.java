package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.service.ListAnalyzerService;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.ListAnalyzerConfigurationsFromProjectService;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.UpdateAnalyzerConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAnalyzerConfigurationServiceTest {

  @Mock private UpdateAnalyzerConfigurationPort updateConfigurationPortMock;

  @Mock private GetAnalyzerConfigurationPort getConfigurationPortMock;

  private UpdateAnalyzerConfigurationService testSubject;
  @Mock private ListAnalyzerService listAnalyzerServiceMock;

  @Mock
  private ListAnalyzerConfigurationsFromProjectService
      listAnalyzerConfigurationsFromProjectServiceMock;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new UpdateAnalyzerConfigurationService(
            updateConfigurationPortMock,
            getConfigurationPortMock,
            listAnalyzerServiceMock,
            listAnalyzerConfigurationsFromProjectServiceMock);
  }

  @Test
  void updateAnalyzerConfigurationUpdatesNameAndEnabled(
      @Mock AnalyzerConfiguration existingConfigurationMock) {
    // given
    long configurationId = 1L;
    String newConfigurationName = "new analyzer name";

    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand(newConfigurationName, false);

    when(getConfigurationPortMock.getAnalyzerConfiguration(configurationId))
        .thenReturn(existingConfigurationMock);

    when(listAnalyzerServiceMock.listAvailableAnalyzers())
        .thenReturn(Collections.singletonList("new analyzer name"));

    // when
    testSubject.update(command, 1L, 2L);

    // then
    verify(existingConfigurationMock, never()).setId(any());
    verify(existingConfigurationMock).setAnalyzerName(newConfigurationName);
    verify(existingConfigurationMock).setEnabled(false);

    verify(updateConfigurationPortMock).update(existingConfigurationMock);
  }
}
