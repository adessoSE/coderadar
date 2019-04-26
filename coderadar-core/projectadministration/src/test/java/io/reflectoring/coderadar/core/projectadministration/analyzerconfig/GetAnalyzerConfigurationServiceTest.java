package io.reflectoring.coderadar.core.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.service.analyzerconfig.GetAnalyzerConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GetAnalyzerConfigurationServiceTest {
  @Mock private GetAnalyzerConfigurationPort port;
  private GetAnalyzerConfigurationService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new GetAnalyzerConfigurationService(port);
  }

  @Test
  void returnsAnalyzerConfigurationWithIdOne() {
    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setId(1L);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);
    Mockito.when(port.getAnalyzerConfiguration(1L)).thenReturn(analyzerConfiguration);

    GetAnalyzerConfigurationResponse response = testSubject.getSingleAnalyzerConfiguration(1L);

    Assertions.assertEquals(analyzerConfiguration.getAnalyzerName(), response.getAnalyzerName());
    Assertions.assertEquals(analyzerConfiguration.getEnabled(), response.getEnabled());
    Assertions.assertEquals(analyzerConfiguration.getId(), response.getId());
  }
}
