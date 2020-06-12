package io.reflectoring.coderadar.rest.unit.useradministration;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.rest.useradministration.ListUsersController;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListUsersUseCase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ListUsersControllerTest {
  private final ListUsersUseCase listUsersUseCase = mock(ListUsersUseCase.class);
  private final ListUsersController testController = new ListUsersController(listUsersUseCase);

  @Test
  public void testListUsers() {
    Mockito.when(listUsersUseCase.listUsers())
        .thenReturn(
            Arrays.asList(
                new User().setId(2L).setUsername("TestUser1"),
                new User().setId(3L).setUsername("TestUser2")));

    ResponseEntity<List<GetUserResponse>> response = testController.listUsers();
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(2, response.getBody().size());
    Assertions.assertEquals(2L, response.getBody().get(0).getId());
    Assertions.assertEquals("TestUser1", response.getBody().get(0).getUsername());
    Assertions.assertEquals(3L, response.getBody().get(1).getId());
    Assertions.assertEquals("TestUser2", response.getBody().get(1).getUsername());
  }
}
