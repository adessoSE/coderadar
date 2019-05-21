package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.anyLong;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.DeleteAnalyzerConfigurationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class DeleteAnalyzerConfigurationServiceTest {
  @Mock private DeleteAnalyzerConfigurationPort port;
  @Mock private GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;

  @InjectMocks private DeleteAnalyzerConfigurationService testSubject;

  @Test
  void deleteAnalyzerConfigurationWithIdOne() {
    Mockito.when(getAnalyzerConfigurationPort.getAnalyzerConfiguration(anyLong()))
        .thenReturn(java.util.Optional.of(new AnalyzerConfiguration()));
    testSubject.deleteAnalyzerConfiguration(1L);

    Mockito.verify(port, Mockito.times(1)).deleteAnalyzerConfiguration(1L);
  }
}
