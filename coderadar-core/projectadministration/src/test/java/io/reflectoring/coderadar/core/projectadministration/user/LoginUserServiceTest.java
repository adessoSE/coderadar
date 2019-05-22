package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.core.projectadministration.service.user.login.LoginUserService;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.TokenService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class LoginUserServiceTest {
  @Mock private RefreshTokenPort refreshTokenPort;
  @Mock private LoadUserPort loadUserPort;
  @Mock private TokenService tokenService;
  @Mock private AuthenticationManager authenticationManager;

  @InjectMocks private LoginUserService testSubject;

  @Test
  void loginUserWithUsernameAndPassword() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    Mockito.when(tokenService.generateAccessToken(user.getId(), user.getUsername()))
        .thenReturn("abalfgubhfuo[oi3y0823pdyu");
    Mockito.when(tokenService.generateRefreshToken(user.getId(), user.getUsername()))
        .thenReturn("ift021789f21897f2187fg");

    Mockito.when(loadUserPort.loadUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

    LoginUserCommand command = new LoginUserCommand("username", "password");
    LoginUserResponse response = testSubject.login(command);

    Assertions.assertEquals("abalfgubhfuo[oi3y0823pdyu", response.getAccessToken());
    Assertions.assertEquals("ift021789f21897f2187fg", response.getRefreshToken());
  }
}
