package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DeleteFilePatternControllerTest {

  @Mock private DeleteFilePatternFromProjectUseCase deleteFilePatternFromProjectUseCase;
  private DeleteFilePatternController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new DeleteFilePatternController(deleteFilePatternFromProjectUseCase);
  }

  @Test
  public void deleteFilePatternWithIdOne() {
    // TODO
  }
}
