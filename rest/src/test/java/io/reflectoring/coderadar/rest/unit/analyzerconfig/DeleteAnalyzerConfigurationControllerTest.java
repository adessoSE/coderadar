package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.analyzerconfig.DeleteAnalyzerConfigurationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class DeleteAnalyzerConfigurationControllerTest {

  private DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase =
      mock(DeleteAnalyzerConfigurationUseCase.class);

  @Test
  void deleteAnalyzerConfigurationWithIdOne() {
    DeleteAnalyzerConfigurationController testSubject =
        new DeleteAnalyzerConfigurationController(deleteAnalyzerConfigurationUseCase);

    ResponseEntity<String> responseEntity = testSubject.deleteAnalyzerConfiguration(1L);

    Mockito.verify(deleteAnalyzerConfigurationUseCase, Mockito.times(1))
        .deleteAnalyzerConfiguration(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
