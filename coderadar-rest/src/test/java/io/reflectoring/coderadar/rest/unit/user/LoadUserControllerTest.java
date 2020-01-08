package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.rest.user.LoadUserController;
import io.reflectoring.coderadar.rest.LoadUserResponse;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.load.LoadUserUseCase;
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

    Mockito.when(loadUserUseCase.loadUser(1L)).thenReturn(new User().setUsername("username").setId(1L));

    ResponseEntity<LoadUserResponse> responseEntity = testSubject.loadUser(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("username", responseEntity.getBody().getUsername());
  }
}
