package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import io.reflectoring.coderadar.rest.filepattern.DeleteFilePatternController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class DeleteFilePatternControllerTest {

  private DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase =
      mock(DeleteFilePatternFromProjectUseCase.class);

  @Test
  void deleteFilePatternWithIdOne() {
    DeleteFilePatternController testSubject =
        new DeleteFilePatternController(deleteFilePatternFromProjectUseCase);

    ResponseEntity<String> responseEntity = testSubject.deleteFilePattern(1L, 2L);

    Mockito.verify(deleteFilePatternFromProjectUseCase, Mockito.times(1)).delete(1L, 2L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
