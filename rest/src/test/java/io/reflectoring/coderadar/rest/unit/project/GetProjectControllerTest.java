package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.get.GetProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GetProjectControllerTest {

  @Mock private GetProjectUseCase getProjectUseCase;
  private GetProjectController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new GetProjectController(getProjectUseCase);
  }

  @Test
  public void returnsProjectWithIdOne() {
    GetProjectResponse project = new GetProjectResponse();
    project.setId(1L);

    Mockito.when(getProjectUseCase.get(1L)).thenReturn(project);

    ResponseEntity<GetProjectResponse> responseEntity = testSubject.getProject(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(project.getId(), responseEntity.getBody().getId());
  }
}
