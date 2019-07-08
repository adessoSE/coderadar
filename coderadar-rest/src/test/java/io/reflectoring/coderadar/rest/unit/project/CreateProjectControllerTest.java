package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectUseCase;
import io.reflectoring.coderadar.rest.IdResponse;
import io.reflectoring.coderadar.rest.project.CreateProjectController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.util.Date;

import static org.mockito.Mockito.mock;

class CreateProjectControllerTest {

  private CreateProjectUseCase createProjectUseCase = mock(CreateProjectUseCase.class);

  @Test
  void createNewProjectSuccessfully() throws MalformedURLException {
    CreateProjectController testSubject = new CreateProjectController(createProjectUseCase);

    CreateProjectCommand command =
        new CreateProjectCommand(
            "test",
            "testUsername",
            "testPassword",
            "http://valid.url",
            true,
            new Date(),
            new Date());

    Mockito.when(createProjectUseCase.createProject(command)).thenReturn(1L);

    ResponseEntity<IdResponse> responseEntity = testSubject.createProject(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
  }
}
