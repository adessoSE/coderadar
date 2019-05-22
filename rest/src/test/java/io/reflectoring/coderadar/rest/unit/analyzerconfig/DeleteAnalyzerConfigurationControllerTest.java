package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.analyzerconfig.DeleteAnalyzerConfigurationController;
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
class DeleteAnalyzerConfigurationControllerTest {

  @Mock private DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase;
  @InjectMocks private DeleteAnalyzerConfigurationController testSubject;

  @Test
  void deleteAnalyzerConfigurationWithIdOne() {
    ResponseEntity<String> responseEntity = testSubject.deleteAnalyzerConfiguration(1L);

    Mockito.verify(deleteAnalyzerConfigurationUseCase, Mockito.times(1))
        .deleteAnalyzerConfiguration(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
