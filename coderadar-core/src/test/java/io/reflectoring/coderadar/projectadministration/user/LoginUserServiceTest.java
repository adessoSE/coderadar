package io.reflectoring.coderadar.projectadministration.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.projectadministration.service.user.login.LoginUserService;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;

class LoginUserServiceTest {
  private RefreshTokenPort refreshTokenPort = mock(RefreshTokenPort.class);
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);
  private TokenService tokenService = mock(TokenService.class);
  private AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

  @Test
  void loginUserWithUsernameAndPassword() {
    LoginUserService testSubject =
        new LoginUserService(loadUserPort, refreshTokenPort, authenticationManager, tokenService);

    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    Mockito.when(tokenService.generateAccessToken(user.getId(), user.getUsername()))
        .thenReturn("abalfgubhfuo[oi3y0823pdyu");
    Mockito.when(tokenService.generateRefreshToken(user.getId(), user.getUsername()))
        .thenReturn("ift021789f21897f2187fg");

    Mockito.when(loadUserPort.loadUserByUsername(user.getUsername())).thenReturn(user);

    LoginUserCommand command = new LoginUserCommand("username", "password");
    LoginUserResponse response = testSubject.login(command);

    Assertions.assertEquals("abalfgubhfuo[oi3y0823pdyu", response.getAccessToken());
    Assertions.assertEquals("ift021789f21897f2187fg", response.getRefreshToken());
  }
}
