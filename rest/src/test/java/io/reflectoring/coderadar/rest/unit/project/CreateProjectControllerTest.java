package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.create.CreateProjectUseCase;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
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
public class CreateProjectControllerTest {

  @Mock private CreateProjectUseCase createProjectUseCase;
  @InjectMocks private CreateProjectController testSubject;

  @Test
  public void createNewProjectSuccessfully() throws MalformedURLException {
    URL url = new URL("http://valid.url");
    CreateProjectCommand command =
        new CreateProjectCommand(
            "test", "testUsername", "testPassword", url, true, new Date(), new Date());

    Mockito.when(createProjectUseCase.createProject(command)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.createProject(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
