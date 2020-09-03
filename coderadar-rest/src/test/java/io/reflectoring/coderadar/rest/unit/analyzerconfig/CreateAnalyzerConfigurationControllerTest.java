package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.analyzerconfig.CreateAnalyzerConfigurationController;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CreateAnalyzerConfigurationControllerTest extends UnitTestTemplate {

  private final CreateAnalyzerConfigurationUseCase createAnalyzerConfigurationUseCase =
      mock(CreateAnalyzerConfigurationUseCase.class);

  @Test
  void testCreateAnalyzer() {
    CreateAnalyzerConfigurationController testSubject =
        new CreateAnalyzerConfigurationController(
            createAnalyzerConfigurationUseCase, authenticationService);

    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);
    Mockito.when(createAnalyzerConfigurationUseCase.create(command, 5L)).thenReturn(1L);

    ResponseEntity<Object> responseEntity = testSubject.addAnalyzerConfiguration(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(1L, ((IdResponse) responseEntity.getBody()).getId());
  }
}
