package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.rest.user.GetUserController;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.load.GetUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class GetUserControllerTest {

  private GetUserUseCase getUserUseCase = mock(GetUserUseCase.class);

  @Test
  void loadUserWithIdOne() {
    GetUserController testSubject = new GetUserController(getUserUseCase);

    Mockito.when(getUserUseCase.getUser(1L))
        .thenReturn(new User().setUsername("username").setId(1L));

    ResponseEntity<GetUserResponse> responseEntity = testSubject.loadUser(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("username", responseEntity.getBody().getUsername());
  }
}
