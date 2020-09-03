package io.reflectoring.coderadar.rest.unit.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import io.reflectoring.coderadar.rest.filepattern.GetFilePatternController;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetFilePatternControllerTest extends UnitTestTemplate {

  private final GetFilePatternUseCase getFilePatternUseCase = mock(GetFilePatternUseCase.class);

  @Test
  void testDeleteFilePattern() {
    GetFilePatternController testSubject =
        new GetFilePatternController(getFilePatternUseCase, authenticationService);

    FilePattern filePattern = new FilePattern(1L, "**/*.java", InclusionType.INCLUDE);

    Mockito.when(getFilePatternUseCase.get(1L)).thenReturn(filePattern);
    ResponseEntity<GetFilePatternResponse> responseEntity = testSubject.getFilePattern(0L, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(filePattern.getId(), responseEntity.getBody().getId());
    Assertions.assertEquals(filePattern.getPattern(), responseEntity.getBody().getPattern());
    Assertions.assertEquals(
        filePattern.getInclusionType(), responseEntity.getBody().getInclusionType());
  }
}
