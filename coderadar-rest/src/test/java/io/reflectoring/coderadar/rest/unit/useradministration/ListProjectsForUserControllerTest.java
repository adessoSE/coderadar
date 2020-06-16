package io.reflectoring.coderadar.rest.unit.useradministration;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.ListProjectsForUserController;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ListProjectsForUserControllerTest extends UnitTestTemplate {
  private final ListProjectsForUserUseCase listProjectsForUserUseCase =
      mock(ListProjectsForUserUseCase.class);

  private final ListProjectsForUserController testController =
      new ListProjectsForUserController(listProjectsForUserUseCase);

  @Test
  public void testListProjectsForUser() {
    // Set up
    Mockito.when(listProjectsForUserUseCase.listProjects(1L))
        .thenReturn(Collections.singletonList(new Project().setName("TestProject1").setId(2L)));

    ResponseEntity<List<GetProjectResponse>> response = testController.listProjectsForUser(1L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(1L, response.getBody().size());
    Assertions.assertEquals(2L, response.getBody().get(0).getId());
    Assertions.assertEquals("TestProject1", response.getBody().get(0).getName());
  }

  @Test
  public void testListProjectsForUserThrowsWhenUserNotFound() { // TODO: Exception is caught by the
    // ExceptionHandler, is this test needed?
    Mockito.when(listProjectsForUserUseCase.listProjects(1L))
        .thenThrow(new UserNotFoundException(1L));
    Assertions.assertThrows(
        UserNotFoundException.class, () -> testController.listProjectsForUser(1L));
  }
}
