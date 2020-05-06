package io.reflectoring.coderadar.rest.unit.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.rest.useradministration.ListUsersForProjectController;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListUsersForProjectUseCase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ListUsersForProjectControllerTest {
  private final ListUsersForProjectUseCase listUsersForProjectUseCase =
      mock(ListUsersForProjectUseCase.class);
  private final ListUsersForProjectController testController =
      new ListUsersForProjectController(listUsersForProjectUseCase);

  @Test
  public void testListUsersForProject() {
    Mockito.when(listUsersForProjectUseCase.listUsers(1L))
        .thenReturn(
            Arrays.asList(
                new User().setId(2L).setUsername("TestUser1"),
                new User().setId(3L).setUsername("TestUser2")));

    ResponseEntity<List<GetUserResponse>> response = testController.listTeamsForUser(1L);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(2, response.getBody().size());
    Assertions.assertEquals(2L, response.getBody().get(0).getId());
    Assertions.assertEquals("TestUser1", response.getBody().get(0).getUsername());
    Assertions.assertEquals(3L, response.getBody().get(1).getId());
    Assertions.assertEquals("TestUser2", response.getBody().get(1).getUsername());
  }

  @Test
  public void testListUsersForProjectThrowsExceptionWhenProjectNotFound() {
    Mockito.doThrow(ProjectNotFoundException.class).when(listUsersForProjectUseCase).listUsers(1L);
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> testController.listTeamsForUser(1L));
  }
}
