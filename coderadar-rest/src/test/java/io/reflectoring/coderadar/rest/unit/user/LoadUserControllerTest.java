package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.user.load.LoadUserUseCase;
import io.reflectoring.coderadar.rest.user.LoadUserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class LoadUserControllerTest {

  private LoadUserUseCase loadUserUseCase = mock(LoadUserUseCase.class);

  @Test
  void loadUserWithIdOne() {
    LoadUserController testSubject = new LoadUserController(loadUserUseCase);

    LoadUserResponse user = new LoadUserResponse(1L, "username");

    Mockito.when(loadUserUseCase.loadUser(1L)).thenReturn(user);

    ResponseEntity<LoadUserResponse> responseEntity = testSubject.loadUser(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("username", responseEntity.getBody().getUsername());
  }
}
