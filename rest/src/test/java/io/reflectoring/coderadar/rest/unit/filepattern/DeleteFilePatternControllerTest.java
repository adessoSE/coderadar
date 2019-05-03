package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
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
public class DeleteFilePatternControllerTest {

  @Mock private DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase;
  private DeleteFilePatternController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new DeleteFilePatternController(deleteFilePatternFromProjectUseCase);
  }

  @Test
  public void deleteFilePatternWithIdOne() {
    ResponseEntity<String> responseEntity = testSubject.deleteFilePattern(1L);

    Mockito.verify(deleteFilePatternFromProjectUseCase, Mockito.times(1)).delete(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
