package io.reflectoring.coderadar.useradministration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.useradministration.domain.RefreshToken;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.RefreshTokenPort;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserResponse;
import io.reflectoring.coderadar.useradministration.service.login.LoginUserService;
import io.reflectoring.coderadar.useradministration.service.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

  @Mock private GetUserPort getUserPortMock;

  @Mock private RefreshTokenPort refreshTokenPortMock;

  @Mock private AuthenticationManager authenticationManagerMock;

  @Mock private TokenService tokenServiceMock;

  private LoginUserService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new LoginUserService(
            getUserPortMock, refreshTokenPortMock, authenticationManagerMock, tokenServiceMock);
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
        new LoginUserResponse(expectedAccessToken, expectedRefreshToken, userId);
    RefreshToken expectedRefreshTokenEntity = new RefreshToken().setToken(expectedRefreshToken);

    LoginUserCommand command = new LoginUserCommand(username, password);

    when(getUserPortMock.getUserByUsername(user.getUsername())).thenReturn(user);

    when(tokenServiceMock.generateAccessToken(userId, username)).thenReturn(expectedAccessToken);
    when(tokenServiceMock.generateRefreshToken(userId, username)).thenReturn(expectedRefreshToken);

    // when
    LoginUserResponse actualResponse = testSubject.login(command);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);

    verify(authenticationManagerMock).authenticate(expectedToken);
    verify(refreshTokenPortMock).saveToken(expectedRefreshTokenEntity, user.getId());
  }
}
