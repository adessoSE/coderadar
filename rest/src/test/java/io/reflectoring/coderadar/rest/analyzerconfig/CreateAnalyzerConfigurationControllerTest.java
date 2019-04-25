package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.create.CreateAnalyzerConfigurationUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CreateAnalyzerConfigurationControllerTest {

  @Mock private CreateAnalyzerConfigurationUseCase createAnalyzerConfigurationUseCase;
  private CreateAnalyzerConfigurationController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new CreateAnalyzerConfigurationController(createAnalyzerConfigurationUseCase);
  }

  @Test
  public void returnsIdOneForNewAnalyzerConfiguration() {
    CreateAnalyzerConfigurationCommand command =
        new CreateAnalyzerConfigurationCommand("analyzer", true);
    Mockito.when(createAnalyzerConfigurationUseCase.create(command, 5L)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.addAnalyzerConfiguration(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
