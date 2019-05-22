package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.analyzerconfig.CreateAnalyzerConfigurationController;
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
class CreateAnalyzerConfigurationControllerTest {

  @Mock private CreateAnalyzerConfigurationUseCase createAnalyzerConfigurationUseCase;
  @InjectMocks private CreateAnalyzerConfigurationController testSubject;

  @Test
  void returnsIdOneForNewAnalyzerConfiguration() {
    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);
    Mockito.when(createAnalyzerConfigurationUseCase.create(command, 5L)).thenReturn(1L);

    ResponseEntity<IdResponse> responseEntity = testSubject.addAnalyzerConfiguration(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
  }
}
