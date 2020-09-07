package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.service.ListAnalyzersService;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.UpdateAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.ListAnalyzerConfigurationsService;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.UpdateAnalyzerConfigurationService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateAnalyzerConfigurationServiceTest {

  @Mock private UpdateAnalyzerConfigurationPort updateConfigurationPortMock;

  @Mock private GetAnalyzerConfigurationPort getConfigurationPortMock;

  private UpdateAnalyzerConfigurationService testSubject;
  @Mock private ListAnalyzersService listAnalyzerServiceMock;

  @Mock private ListAnalyzerConfigurationsService listAnalyzerConfigurationsFromProjectServiceMock;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new UpdateAnalyzerConfigurationService(
            getConfigurationPortMock,
            updateConfigurationPortMock,
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
    verify(existingConfigurationMock, never()).setId(anyLong());
    verify(existingConfigurationMock).setAnalyzerName(newConfigurationName);
    verify(existingConfigurationMock).setEnabled(false);

    verify(updateConfigurationPortMock).update(existingConfigurationMock);
  }
}
