package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import io.reflectoring.coderadar.rest.analyzerconfig.DeleteAnalyzerConfigurationController;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DeleteAnalyzerConfigurationControllerTest extends UnitTestTemplate {

  private final DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase =
      mock(DeleteAnalyzerConfigurationUseCase.class);

  @Test
  void testDeleteAnalyzerConfiguration() {
    DeleteAnalyzerConfigurationController testSubject =
        new DeleteAnalyzerConfigurationController(
            deleteAnalyzerConfigurationUseCase, authenticationService);

    ResponseEntity<HttpStatus> responseEntity = testSubject.deleteAnalyzerConfiguration(1L, 2L);

    Mockito.verify(deleteAnalyzerConfigurationUseCase, Mockito.times(1))
        .deleteAnalyzerConfiguration(1L, 2L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
