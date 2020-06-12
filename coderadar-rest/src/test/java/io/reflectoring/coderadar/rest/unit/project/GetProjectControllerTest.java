package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectUseCase;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.rest.project.GetProjectController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class GetProjectControllerTest {

  private final GetProjectUseCase getProjectUseCase = mock(GetProjectUseCase.class);

  @Test
  void returnsProjectWithIdOne() {
    GetProjectController testSubject = new GetProjectController(getProjectUseCase);

    Project project = new Project();
    project.setId(1L);

    Mockito.when(getProjectUseCase.get(1L)).thenReturn(project);

    ResponseEntity<GetProjectResponse> responseEntity = testSubject.getProject(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(project.getId(), responseEntity.getBody().getId());
  }
}
