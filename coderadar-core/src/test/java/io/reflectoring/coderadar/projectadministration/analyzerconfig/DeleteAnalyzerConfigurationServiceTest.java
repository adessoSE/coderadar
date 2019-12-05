package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.DeleteAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.DeleteAnalyzerConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteAnalyzerConfigurationServiceTest {

  @Mock private DeleteAnalyzerConfigurationPort deleteConfigurationPortMock;

  @Mock private GetAnalyzerConfigurationPort getAnalyzerConfigurationPort;

  private DeleteAnalyzerConfigurationService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new DeleteAnalyzerConfigurationService(
            deleteConfigurationPortMock, getAnalyzerConfigurationPort);
    when(getAnalyzerConfigurationPort.getAnalyzerConfiguration(anyLong()))
        .thenReturn(new AnalyzerConfiguration());
  }

  @Test
  void deleteAnalyzerConfigurationDeletesConfigurationWithGivenId() {
    // given
    long configurationId = 1L;

    // when
    testSubject.deleteAnalyzerConfiguration(configurationId, 2L);

    // then
    verify(deleteConfigurationPortMock).deleteAnalyzerConfiguration(configurationId);
  }
}
