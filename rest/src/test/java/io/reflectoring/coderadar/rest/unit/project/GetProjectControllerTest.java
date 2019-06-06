package io.reflectoring.coderadar.rest.unit.project;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectUseCase;
import io.reflectoring.coderadar.rest.project.GetProjectController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetProjectControllerTest {

  private GetProjectUseCase getProjectUseCase = mock(GetProjectUseCase.class);

  @Test
  void returnsProjectWithIdOne() {
    GetProjectController testSubject = new GetProjectController(getProjectUseCase);

    GetProjectResponse project = new GetProjectResponse();
    project.setId(1L);

    Mockito.when(getProjectUseCase.get(1L)).thenReturn(project);

    ResponseEntity<GetProjectResponse> responseEntity = testSubject.getProject(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(project.getId(), responseEntity.getBody().getId());
  }
}
