package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoginUserUseCase;
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
public class LoginUserControllerTest {

  @Mock private LoginUserUseCase loginUserUseCase;
  private LoginUserController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new LoginUserController(loginUserUseCase);
  }

  @Test
  public void loginUserWithUsernameAndPassword() {
    LoginUserCommand command = new LoginUserCommand("username", "password");
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    user.setPassword("password");

    Mockito.when(loginUserUseCase.login(command)).thenReturn(user);

    ResponseEntity<User> responseEntity = testSubject.login(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1L, responseEntity.getBody().getId().longValue());
    Assertions.assertEquals("username", responseEntity.getBody().getUsername());
    Assertions.assertEquals("password", responseEntity.getBody().getPassword());
  }
}
