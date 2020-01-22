package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.ListAnalyzerConfigurationsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.ListAnalyzerConfigurationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAnalyzerConfigurationsFromProjectServiceTest {

  @Mock private ListAnalyzerConfigurationsPort getConfigurationsPortMock;

  @Mock private GetProjectPort getProjectPort;

  private ListAnalyzerConfigurationsService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new ListAnalyzerConfigurationsService(getConfigurationsPortMock, getProjectPort);
  }

  @Test
  void returnsTwoAnalyzerConfigurationsFromProject() {
    // given
    long projectId = 1L;

    AnalyzerConfiguration analyzerConfiguration1 =
        new AnalyzerConfiguration().setId(1L).setAnalyzerName("analyzer 1").setEnabled(true);
    AnalyzerConfiguration analyzerConfiguration2 =
        new AnalyzerConfiguration().setId(2L).setAnalyzerName("analyzer 2").setEnabled(false);

    List<AnalyzerConfiguration> configurations = new ArrayList<>();
    configurations.add(analyzerConfiguration1);
    configurations.add(analyzerConfiguration2);

    AnalyzerConfiguration expectedResponse1 = new AnalyzerConfiguration(1L, "analyzer 1", true);
    AnalyzerConfiguration expectedResponse2 = new AnalyzerConfiguration(2L, "analyzer 2", false);

    when(getProjectPort.existsById(anyLong())).thenReturn(true);

    when(getConfigurationsPortMock.listAnalyzerConfigurations(projectId))
        .thenReturn(configurations);

    // given
    List<AnalyzerConfiguration> actualResponse = testSubject.get(projectId);

    // then
    assertThat(actualResponse).containsExactly(expectedResponse1, expectedResponse2);
  }
}
