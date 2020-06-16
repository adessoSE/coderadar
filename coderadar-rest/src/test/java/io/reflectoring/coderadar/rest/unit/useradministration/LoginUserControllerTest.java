package io.reflectoring.coderadar.rest.unit.useradministration;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.rest.unit.UnitTestTemplate;
import io.reflectoring.coderadar.rest.useradministration.LoginUserController;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserResponse;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class LoginUserControllerTest extends UnitTestTemplate {

  private final LoginUserUseCase loginUserUseCase = mock(LoginUserUseCase.class);

  @Test
  void loginUserWithUsernameAndPassword() {
    LoginUserController testSubject = new LoginUserController(loginUserUseCase);

    LoginUserCommand command = new LoginUserCommand("username", "password");
    LoginUserResponse loginUserResponse = new LoginUserResponse("accessToken", "refreshToken", 1L);

    Mockito.when(loginUserUseCase.login(command)).thenReturn(loginUserResponse);

    ResponseEntity<Object> responseEntity = testSubject.login(command);

    Assertions.assertTrue(responseEntity.getBody() instanceof LoginUserResponse);
    LoginUserResponse responseBody = (LoginUserResponse) responseEntity.getBody();
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(loginUserResponse.getAccessToken(), responseBody.getAccessToken());
    Assertions.assertEquals(loginUserResponse.getRefreshToken(), responseBody.getRefreshToken());
    Assertions.assertEquals(loginUserResponse.getUserId(), responseBody.getUserId());
  }
}
