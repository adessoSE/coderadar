package io.reflectoring.coderadar.projectadministration.analyzerconfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.projectadministration.service.analyzerconfig.GetAnalyzerConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAnalyzerConfigurationServiceTest {

  @Mock private GetAnalyzerConfigurationPort getConfigurationPortMock;

  private GetAnalyzerConfigurationService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new GetAnalyzerConfigurationService(getConfigurationPortMock);
  }

  @Test
  void returnsAnalyzerConfigurationWithExpectedId() {
    // given
    long configurationId = 1L;
    String analyzerName = "analyzer";
    boolean analyzerEnabled = true;

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration()
            .setId(configurationId)
            .setAnalyzerName(analyzerName)
            .setEnabled(analyzerEnabled);

    GetAnalyzerConfigurationResponse expectedResponse =
        new GetAnalyzerConfigurationResponse(configurationId, analyzerName, analyzerEnabled);

    when(getConfigurationPortMock.getAnalyzerConfiguration(1L)).thenReturn(analyzerConfiguration);

    // when
    GetAnalyzerConfigurationResponse actualResponse =
        testSubject.getSingleAnalyzerConfiguration(1L);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
