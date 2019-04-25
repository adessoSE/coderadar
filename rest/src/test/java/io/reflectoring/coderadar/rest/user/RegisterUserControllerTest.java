package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RegisterUserControllerTest {

  @Mock private RegisterUserUseCase registerUserUseCase;
  private RegisterUserController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new RegisterUserController(registerUserUseCase);
  }

  @Test
  public void registerUserWithIdOne() {
    RegisterUserCommand command = new RegisterUserCommand("username", "password");

    Mockito.when(registerUserUseCase.register(command)).thenReturn(1L);

    ResponseEntity<Long> responseEntity = testSubject.register(command);

    Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().longValue());
  }
}
