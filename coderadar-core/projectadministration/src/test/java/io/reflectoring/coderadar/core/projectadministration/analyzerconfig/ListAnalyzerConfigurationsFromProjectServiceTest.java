package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.ListAnalyzerConfigurationsFromProjectService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ListAnalyzerConfigurationsFromProjectServiceTest {
  private GetAnalyzerConfigurationsFromProjectPort port =
      mock(GetAnalyzerConfigurationsFromProjectPort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);

  @Test
  void returnsTwoAnalyzerConfigurationsFromProject() {
    ListAnalyzerConfigurationsFromProjectService testSubject =
        new ListAnalyzerConfigurationsFromProjectService(port, getProjectPort);

    Mockito.when(getProjectPort.get(anyLong())).thenReturn(Optional.of(new Project()));

    AnalyzerConfiguration analyzerConfiguration1 = new AnalyzerConfiguration();
    analyzerConfiguration1.setId(1L);
    analyzerConfiguration1.setAnalyzerName("analyzer 1");
    analyzerConfiguration1.setEnabled(true);
    AnalyzerConfiguration analyzerConfiguration2 = new AnalyzerConfiguration();
    analyzerConfiguration2.setId(2L);
    analyzerConfiguration2.setAnalyzerName("analyzer 2");
    analyzerConfiguration2.setEnabled(false);
    List<AnalyzerConfiguration> configurations = new ArrayList<>();
    configurations.add(analyzerConfiguration1);
    configurations.add(analyzerConfiguration2);

    Mockito.when(port.get(1L)).thenReturn(configurations);

    List<GetAnalyzerConfigurationResponse> response = testSubject.get(1L);

    Assertions.assertEquals(configurations.size(), response.size());
    Assertions.assertEquals(analyzerConfiguration1.getId(), response.get(0).getId());
    Assertions.assertEquals(analyzerConfiguration2.getId(), response.get(1).getId());
    Assertions.assertEquals(
        analyzerConfiguration1.getAnalyzerName(), response.get(0).getAnalyzerName());
    Assertions.assertEquals(
        analyzerConfiguration2.getAnalyzerName(), response.get(1).getAnalyzerName());
    Assertions.assertEquals(analyzerConfiguration1.getEnabled(), response.get(0).getEnabled());
    Assertions.assertEquals(analyzerConfiguration2.getEnabled(), response.get(1).getEnabled());
  }
}
