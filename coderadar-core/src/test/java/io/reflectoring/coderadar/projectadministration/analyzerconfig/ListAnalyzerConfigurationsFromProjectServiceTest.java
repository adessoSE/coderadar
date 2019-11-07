package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.ListAnalyzerConfigurationsFromProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAnalyzerConfigurationsFromProjectServiceTest {

  @Mock private GetAnalyzerConfigurationsFromProjectPort getConfigurationsPortMock;

  private ListAnalyzerConfigurationsFromProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new ListAnalyzerConfigurationsFromProjectService(getConfigurationsPortMock);
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

    GetAnalyzerConfigurationResponse expectedResponse1 =
        new GetAnalyzerConfigurationResponse(1L, "analyzer 1", true);
    GetAnalyzerConfigurationResponse expectedResponse2 =
        new GetAnalyzerConfigurationResponse(2L, "analyzer 2", false);

    when(getConfigurationsPortMock.get(projectId)).thenReturn(configurations);

    // given
    List<GetAnalyzerConfigurationResponse> actualResponse = testSubject.get(projectId);

    // then
    assertThat(actualResponse).containsExactly(expectedResponse1, expectedResponse2);
  }
}
