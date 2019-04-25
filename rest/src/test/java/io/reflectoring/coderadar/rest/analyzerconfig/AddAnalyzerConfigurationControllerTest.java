package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.AddAnalyzerConfigurationUseCase;
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
public class AddAnalyzerConfigurationControllerTest {

  @Mock private AddAnalyzerConfigurationUseCase addAnalyzerConfigurationUseCase;
  private AddAnalyzerConfigurationController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new AddAnalyzerConfigurationController(addAnalyzerConfigurationUseCase);
  }

  @Test
  public void returnsIdOneForNewAnalyzerConfiguration() {
    AddAnalyzerConfigurationCommand command =
        new AddAnalyzerConfigurationCommand(5L, "analyzer", true);
    Mockito.when(addAnalyzerConfigurationUseCase.add(command)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.addAnalyzerConfiguration(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
