package io.reflectoring.coderadar.rest.unit.project;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.rest.project.CreateProjectController;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import java.net.MalformedURLException;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CreateProjectControllerTest extends UnitTestTemplate {

  private final CreateProjectUseCase createProjectUseCase = mock(CreateProjectUseCase.class);

  @Test
  void createNewProjectSuccessfully() throws MalformedURLException {
    CreateProjectController testSubject = new CreateProjectController(createProjectUseCase);

    CreateProjectCommand command =
        new CreateProjectCommand(
            "test", "testUsername", "testPassword", "http://valid.url", true, new Date(), "master");

    Mockito.when(createProjectUseCase.createProject(command)).thenReturn(1L);

    ResponseEntity<IdResponse> responseEntity = testSubject.createProject(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    Assertions.assertEquals(1L, responseEntity.getBody().getId());
  }
}
