package io.reflectoring.coderadar.projectadministration.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.projectadministration.service.user.login.LoginUserService;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

  @Mock private LoadUserPort loadUserPortMock;

  @Mock private RefreshTokenPort refreshTokenPortMock;

  @Mock private AuthenticationManager authenticationManagerMock;

  @Mock private TokenService tokenServiceMock;

  private LoginUserService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new LoginUserService(
            loadUserPortMock, refreshTokenPortMock, authenticationManagerMock, tokenServiceMock);
  }

  @Test
  void loginUserWithUsernameAndPassword() {
    // when
    long userId = 1L;
    String username = "username";
    String password = "password";
    String expectedAccessToken = "abalfgubhfuo[oi3y0823pdyu";
    String expectedRefreshToken = "ift021789f21897f2187fg";
    User user = new User().setId(userId).setUsername(username);

    UsernamePasswordAuthenticationToken expectedToken =
        new UsernamePasswordAuthenticationToken(username, password);
    LoginUserResponse expectedResponse =
        new LoginUserResponse(expectedAccessToken, expectedRefreshToken);
    RefreshToken expectedRefreshTokenEntity =
        new RefreshToken().setToken(expectedRefreshToken).setUser(user);

    LoginUserCommand command = new LoginUserCommand(username, password);

    when(loadUserPortMock.loadUserByUsername(user.getUsername())).thenReturn(user);

    when(tokenServiceMock.generateAccessToken(userId, username)).thenReturn(expectedAccessToken);
    when(tokenServiceMock.generateRefreshToken(userId, username)).thenReturn(expectedRefreshToken);

    // when
    LoginUserResponse actualResponse = testSubject.login(command);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);

    verify(authenticationManagerMock).authenticate(expectedToken);
    verify(refreshTokenPortMock).saveToken(expectedRefreshTokenEntity);
  }
}
