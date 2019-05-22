package io.reflectoring.coderadar.rest.unit.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.coderadar.rest.filepattern.ListFilePatternsOfProjectController;
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
class ListFilePatternsOfProjectControllerTest {

  @Mock private ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase;
  @InjectMocks private ListFilePatternsOfProjectController testSubject;

  @Test
  void returnsModulesForProjectWithIdOne() {
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
