package io.reflectoring.coderadar.rest.unit.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import io.reflectoring.coderadar.rest.project.DeleteProjectController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class DeleteProjectControllerTest {

  private DeleteProjectUseCase deleteProjectUseCase = mock(DeleteProjectUseCase.class);

  @Test
  void deleteProjectWithIdOne() throws JsonProcessingException, ProjectIsBeingProcessedException {
    DeleteProjectController testSubject = new DeleteProjectController(deleteProjectUseCase);

    ResponseEntity<String> responseEntity = testSubject.deleteProject(1L);
    Mockito.verify(deleteProjectUseCase, Mockito.times(1)).delete(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
