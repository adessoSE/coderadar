package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.update.UpdateAnalyzerConfigurationUseCase;
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
class UpdateAnalyzerConfigurationControllerTest {

  @Mock private UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase;
  @InjectMocks private UpdateAnalyzerConfigurationController testSubject;

  @Test
  void updateAnalyzerConfigurationWithIdOne() {
    UpdateAnalyzerConfigurationCommand command =
        new UpdateAnalyzerConfigurationCommand("analyzer", true);
    ResponseEntity<String> responseEntity = testSubject.updateAnalyzerConfiguration(command, 1L);

    Mockito.verify(updateAnalyzerConfigurationUseCase, Mockito.times(1)).update(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
