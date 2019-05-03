package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UpdateFilePatternControllerTest {

  @Mock private UpdateFilePatternUseCase updateFilePatternForProjectUseCase;
  @InjectMocks private UpdateFilePatternController testSubject;

  @Test
  void updateFilePatternWithIdOne() {
    Assertions.fail();
  }
}
