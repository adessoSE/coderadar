package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.analyzerconfig.GetAnalyzerConfigurationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GetAnalyzerConfigurationControllerTest {

  @Mock private GetAnalyzerConfigurationUseCase getAnalyzerConfigurationUseCase;
  @InjectMocks private GetAnalyzerConfigurationController testSubject;

  @Test
  void returnsAnalyzerConfigurationWithIdOne() {
    GetAnalyzerConfigurationResponse analyzerConfiguration =
        new GetAnalyzerConfigurationResponse(1L, "analyzer", true);

    Mockito.when(getAnalyzerConfigurationUseCase.getSingleAnalyzerConfiguration(1L))
        .thenReturn(analyzerConfiguration);

    ResponseEntity<GetAnalyzerConfigurationResponse> responseEntity =
        testSubject.getAnalyzerConfiguration(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
    Assertions.assertEquals("analyzer", responseEntity.getBody().getAnalyzerName());
    Assertions.assertEquals(true, responseEntity.getBody().getEnabled());
  }
}
