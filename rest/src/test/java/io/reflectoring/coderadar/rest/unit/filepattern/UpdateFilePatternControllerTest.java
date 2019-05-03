package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UpdateFilePatternControllerTest {

  @Mock private UpdateFilePatternUseCase updateFilePatternForProjectUseCase;
  @InjectMocks private UpdateFilePatternController testSubject;

  @Test
  void updateFilePatternWithIdOne() {
    UpdateFilePatternCommand command =
        new UpdateFilePatternCommand("**/*.java", InclusionType.EXCLUDE);
    ResponseEntity<String> responseEntity = testSubject.updateFilePattern(command, 1L);

    Mockito.verify(updateFilePatternForProjectUseCase, Mockito.times(1))
        .updateFilePattern(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
