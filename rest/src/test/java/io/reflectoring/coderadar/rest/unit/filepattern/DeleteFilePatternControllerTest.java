package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;

class DeleteFilePatternControllerTest {

  private DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase = mock(DeleteFilePatternFromProjectUseCase.class);

  @Test
  void deleteFilePatternWithIdOne() {
    DeleteFilePatternController testSubject = new DeleteFilePatternController(deleteFilePatternFromProjectUseCase);

    ResponseEntity<String> responseEntity = testSubject.deleteFilePattern(1L);

    Mockito.verify(deleteFilePatternFromProjectUseCase, Mockito.times(1)).delete(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
