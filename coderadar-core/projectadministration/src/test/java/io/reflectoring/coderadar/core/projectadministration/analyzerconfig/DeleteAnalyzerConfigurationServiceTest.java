package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.DeleteAnalyzerConfigurationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class DeleteAnalyzerConfigurationServiceTest {
  private DeleteAnalyzerConfigurationPort port = mock(DeleteAnalyzerConfigurationPort.class);
  private GetAnalyzerConfigurationPort getAnalyzerConfigurationPort =
      mock(GetAnalyzerConfigurationPort.class);

  @Test
  void deleteAnalyzerConfigurationWithIdOne() {
    DeleteAnalyzerConfigurationService testSubject =
        new DeleteAnalyzerConfigurationService(port, getAnalyzerConfigurationPort);

    Mockito.when(getAnalyzerConfigurationPort.getAnalyzerConfiguration(anyLong()))
        .thenReturn(java.util.Optional.of(new AnalyzerConfiguration()));
    testSubject.deleteAnalyzerConfiguration(1L);

    Mockito.verify(port, Mockito.times(1)).deleteAnalyzerConfiguration(1L);
  }
}
