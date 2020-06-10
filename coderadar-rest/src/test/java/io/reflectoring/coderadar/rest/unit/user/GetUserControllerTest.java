package io.reflectoring.coderadar.rest.unit.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.rest.useradministration.GetUserController;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.get.GetUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetUserControllerTest {

  private final GetUserUseCase getUserUseCase = mock(GetUserUseCase.class);

  @Test
  void loadUserWithIdOne() {
    GetUserController testSubject = new GetUserController(getUserUseCase);

    Mockito.when(getUserUseCase.getUser(1L))
        .thenReturn(new User().setUsername("username").setId(1L));

    ResponseEntity<GetUserResponse> responseEntity = testSubject.getUser(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("username", responseEntity.getBody().getUsername());
  }
}
