package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.rest.useradministration.RegisterUserController;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class RegisterUserControllerTest {

  private final RegisterUserUseCase registerUserUseCase = mock(RegisterUserUseCase.class);

  @Test
  void registerUserWithIdOne() {
    RegisterUserController testSubject = new RegisterUserController(registerUserUseCase);

    RegisterUserCommand command = new RegisterUserCommand("username", "password");

    Mockito.when(registerUserUseCase.register(command)).thenReturn(1L);

    ResponseEntity<IdResponse> responseEntity = testSubject.register(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId());
  }
}
