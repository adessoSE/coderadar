package io.reflectoring.coderadar.rest.unit.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import io.reflectoring.coderadar.rest.filepattern.DeleteFilePatternController;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DeleteFilePatternControllerTest extends UnitTestTemplate {

  private final DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase =
      mock(DeleteFilePatternFromProjectUseCase.class);

  @Test
  void testDeleteFilePattern() {
    DeleteFilePatternController testSubject =
        new DeleteFilePatternController(deleteFilePatternFromProjectUseCase, authenticationService);

    ResponseEntity<HttpStatus> responseEntity = testSubject.deleteFilePattern(1L, 2L);

    Mockito.verify(deleteFilePatternFromProjectUseCase, Mockito.times(1)).delete(1L, 2L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
