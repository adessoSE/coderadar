package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
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
class GetFilePatternControllerTest {

  @Mock private GetFilePatternUseCase getFilePatternUseCase;
  @InjectMocks private GetFilePatternController testSubject;

  @Test
  void returnsFilePatternWithIdOne() {
    GetFilePatternResponse filePattern =
        new GetFilePatternResponse(1L, "**/*.java", InclusionType.INCLUDE);

    Mockito.when(getFilePatternUseCase.get(1L)).thenReturn(filePattern);
    ResponseEntity<GetFilePatternResponse> responseEntity = testSubject.getFilePattern(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(filePattern.getId(), responseEntity.getBody().getId());
    Assertions.assertEquals(filePattern.getPattern(), responseEntity.getBody().getPattern());
    Assertions.assertEquals(
        filePattern.getInclusionType(), responseEntity.getBody().getInclusionType());
  }
}
