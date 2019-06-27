package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserUseCase;
import io.reflectoring.coderadar.rest.user.LoginUserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class LoginUserControllerTest {

  private LoginUserUseCase loginUserUseCase = mock(LoginUserUseCase.class);

  @Test
  void loginUserWithUsernameAndPassword() {
    LoginUserController testSubject = new LoginUserController(loginUserUseCase);

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
