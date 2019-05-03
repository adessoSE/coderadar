package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserUseCase;
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
    LoginUserResponse loginUserResponse = new LoginUserResponse("accessToken", "refreshToken");

    Mockito.when(loginUserUseCase.login(command)).thenReturn(loginUserResponse);

    ResponseEntity<LoginUserResponse> responseEntity = testSubject.login(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(
        loginUserResponse.getAccessToken(), responseEntity.getBody().getAccessToken());
    Assertions.assertEquals(
        loginUserResponse.getRefreshToken(), responseEntity.getBody().getRefreshToken());
  }
}
