package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UpdateFilePatternControllerTest {

  @Mock private UpdateFilePatternUseCase updateFilePatternForProjectUseCase;
  private UpdateFilePatternController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new UpdateFilePatternController(updateFilePatternForProjectUseCase);
  }

  @Test
  public void updateFilePatternWithIdOne() {
    // TODO
  }
}
