package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.DeleteAnalyzerConfigurationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
