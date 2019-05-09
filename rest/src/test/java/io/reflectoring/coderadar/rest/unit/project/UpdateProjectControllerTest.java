package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@ExtendWith(SpringExtension.class)
class UpdateProjectControllerTest {

  @Mock private UpdateProjectUseCase updateProjectUseCase;
  @InjectMocks private UpdateProjectController testSubject;

  @Test
  void updateProjectWithIdOne() throws MalformedURLException {
    URL url = new URL("http://valid.url");
    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "new name", "username", "password", url, true, new Date(), new Date());
    ResponseEntity<String> responseEntity = testSubject.updateProject(command, 1L);

    Mockito.verify(updateProjectUseCase, Mockito.times(1)).update(command, 1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
