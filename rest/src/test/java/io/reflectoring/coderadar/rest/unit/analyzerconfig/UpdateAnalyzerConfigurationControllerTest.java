package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.analyzerconfig.UpdateAnalyzerConfigurationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UpdateAnalyzerConfigurationControllerTest {

  private UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase =
      mock(UpdateAnalyzerConfigurationUseCase.class);

  @Test
  void updateAnalyzerConfigurationWithIdOne() {
    UpdateAnalyzerConfigurationController testSubject =
        new UpdateAnalyzerConfigurationController(updateAnalyzerConfigurationUseCase);

    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("analyzer", true);
    ResponseEntity<String> responseEntity = testSubject.updateAnalyzerConfiguration(command, 1L);

    Mockito.verify(updateAnalyzerConfigurationUseCase, Mockito.times(1)).update(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
