package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import io.reflectoring.coderadar.rest.filepattern.UpdateFilePatternController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class UpdateFilePatternControllerTest {

  private UpdateFilePatternUseCase updateFilePatternForProjectUseCase =
      mock(UpdateFilePatternUseCase.class);

  @Test
  void updateFilePatternWithIdOne() {
    UpdateFilePatternController testSubject =
        new UpdateFilePatternController(updateFilePatternForProjectUseCase);

    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.EXCLUDE);
    ResponseEntity<String> responseEntity = testSubject.updateFilePattern(command, 1L);

    Mockito.verify(updateFilePatternForProjectUseCase, Mockito.times(1))
        .updateFilePattern(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
