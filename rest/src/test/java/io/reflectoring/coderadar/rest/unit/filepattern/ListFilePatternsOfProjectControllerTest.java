package io.reflectoring.coderadar.rest.unit.filepattern;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import io.reflectoring.coderadar.rest.filepattern.ListFilePatternsOfProjectController;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ListFilePatternsOfProjectControllerTest {

  private ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase =
      mock(ListFilePatternsOfProjectUseCase.class);

  @Test
  void returnsModulesForProjectWithIdOne() {
    ListFilePatternsOfProjectController testSubject =
        new ListFilePatternsOfProjectController(listFilePatternsOfProjectUseCase);

    GetFilePatternResponse response1 =
        new GetFilePatternResponse(1L, "**/*.java", InclusionType.INCLUDE);
    GetFilePatternResponse response2 =
        new GetFilePatternResponse(2L, "**/*.xml", InclusionType.EXCLUDE);
    List<GetFilePatternResponse> responses = new ArrayList<>();
    responses.add(response1);
    responses.add(response2);

    Mockito.when(listFilePatternsOfProjectUseCase.listFilePatterns(1L)).thenReturn(responses);

    ResponseEntity<List<GetFilePatternResponse>> responseEntity = testSubject.listFilePatterns(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(responses.size(), responseEntity.getBody().size());
  }
}
