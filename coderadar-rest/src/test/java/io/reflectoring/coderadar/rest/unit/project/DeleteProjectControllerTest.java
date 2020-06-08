package io.reflectoring.coderadar.rest.unit.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import io.reflectoring.coderadar.rest.project.DeleteProjectController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class DeleteProjectControllerTest {

  private final DeleteProjectUseCase deleteProjectUseCase = mock(DeleteProjectUseCase.class);

  @Test
  void deleteProjectWithIdOne() {
    DeleteProjectController testSubject = new DeleteProjectController(deleteProjectUseCase);

    ResponseEntity<HttpStatus> responseEntity = testSubject.deleteProject(1L);
    Mockito.verify(deleteProjectUseCase, Mockito.times(1)).delete(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
