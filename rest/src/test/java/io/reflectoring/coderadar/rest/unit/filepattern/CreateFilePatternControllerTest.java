package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
import io.reflectoring.coderadar.rest.filepattern.CreateFilePatternController;
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
class CreateFilePatternControllerTest {

  @Mock private CreateFilePatternUseCase createFilePatternUseCase;
  @InjectMocks private CreateFilePatternController testSubject;

  @Test
  void createFilePatternSuccessfully() {
    CreateFilePatternCommand command =
        new CreateFilePatternCommand("**/*.java", InclusionType.INCLUDE);
    Mockito.when(createFilePatternUseCase.createFilePattern(command, 5L)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.createFilePattern(command, 5L);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
