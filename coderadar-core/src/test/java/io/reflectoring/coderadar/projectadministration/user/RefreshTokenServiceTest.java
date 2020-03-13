package io.reflectoring.coderadar.projectadministration.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.useradministration.AccessTokenNotExpiredException;
import io.reflectoring.coderadar.useradministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.RefreshToken;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.RefreshTokenPort;
import io.reflectoring.coderadar.useradministration.port.driver.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.useradministration.service.refresh.RefreshTokenService;
import io.reflectoring.coderadar.useradministration.service.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

  @Mock private RefreshTokenPort refreshTokenPort;

  @Mock private TokenService tokenService;

  @Mock private GetUserPort getUserPort;

  private RefreshTokenService testSubject;

  @BeforeEach
  void setUp() {
    testSubject = new RefreshTokenService(getUserPort, refreshTokenPort, tokenService);
  }

  @Test
  void refreshTokenRefreshesTokenWhenAccessTokenIsExpired() {
    // given
    String refreshToken = "refresh-token";
    String accessToken = "access-token";
    RefreshToken refreshTokenEntity = new RefreshToken();
    long userId = 1L;
    String username = "username";
    String expectedAccessToken = "expected-access-token";

    User user = new User().setId(userId).setUsername(username);

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(accessToken, refreshToken);

    when(tokenService.isExpired(accessToken)).thenReturn(true);
    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(refreshTokenEntity);
    when(tokenService.getUsername(refreshToken)).thenReturn(username);
    when(getUserPort.getUserByUsername(username)).thenReturn(user);
    when(tokenService.generateAccessToken(userId, username)).thenReturn(expectedAccessToken);

    // when
    String actualAccessToken = testSubject.refreshToken(refreshTokenCommand);

    // then
    assertThat(actualAccessToken).isEqualTo(expectedAccessToken);
  }

  @Test
  void refreshTokenThrowsExceptionWhenAccessTokenNotExpired() {
    // given
    String refreshToken = "refresh-token";
    String accessToken = "access-token";

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(accessToken, refreshToken);

    when(tokenService.isExpired(accessToken)).thenReturn(false);

    // when / then
    assertThatThrownBy(() -> testSubject.refreshToken(refreshTokenCommand))
        .isInstanceOf(AccessTokenNotExpiredException.class);
  }

  @Test
  void refreshTokenThrowsExceptionWhenTokenWasNotFound() {
    // given
    String accessToken = "access-token";
    String refreshToken = "refresh-token";

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(accessToken, refreshToken);

    when(tokenService.isExpired(accessToken)).thenReturn(true);
    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(null);

    // when / then
    assertThatThrownBy(() -> testSubject.refreshToken(refreshTokenCommand))
        .isInstanceOf(RefreshTokenNotFoundException.class);
  }

  @Test
  void refreshTokenThrowsExceptionWhenUserNotFound() {
    // given
    String refreshToken = "refresh-token";
    String accessToken = "access-token";
    RefreshToken refreshTokenEntity = new RefreshToken();
    String username = "username";

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(accessToken, refreshToken);

    when(tokenService.isExpired(accessToken)).thenReturn(true);
    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(refreshTokenEntity);
    when(tokenService.getUsername(refreshToken)).thenReturn(username);
    when(getUserPort.getUserByUsername(username)).thenReturn(null);

    // when / then
    assertThatThrownBy(() -> testSubject.refreshToken(refreshTokenCommand))
        .isInstanceOf(UserNotFoundException.class);
  }

  @Test
  void createAccessTokenCreatesTokenWhenTokenAndUserFound() {
    // given
    String refreshToken = "refresh-token";
    RefreshToken refreshTokenEntity = new RefreshToken();
    long userId = 1L;
    String username = "username";
    String expectedAccessToken = "expected-access-token";

    User user = new User().setId(userId).setUsername(username);

    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(refreshTokenEntity);
    when(tokenService.getUsername(refreshToken)).thenReturn(username);
    when(getUserPort.getUserByUsername(username)).thenReturn(user);
    when(tokenService.generateAccessToken(userId, username)).thenReturn(expectedAccessToken);

    // when
    String actualAccessToken = testSubject.createAccessToken(refreshToken);

    // then
    assertThat(actualAccessToken).isEqualTo(expectedAccessToken);
  }

  @Test
  void createAccessTokenThrowsExceptionWhenTokenWasNotFound() {
    // given
    String refreshToken = "refresh-token";

    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(null);

    // when / then
    assertThatThrownBy(() -> testSubject.createAccessToken(refreshToken))
        .isInstanceOf(RefreshTokenNotFoundException.class);
  }

  @Test
  void createAccessTokenThrowsExceptionWhenUserNotFound() {
    // given
    String refreshToken = "refresh-token";
    RefreshToken refreshTokenEntity = new RefreshToken();
    String username = "username";

    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(refreshTokenEntity);
    when(tokenService.getUsername(refreshToken)).thenReturn(username);
    when(getUserPort.getUserByUsername(username)).thenReturn(null);

    // when / then
    assertThatThrownBy(() -> testSubject.createAccessToken(refreshToken))
        .isInstanceOf(UserNotFoundException.class);
  }

  @Test
  void checkUserByRefreshTokenReturnsExpectedUser() {
    // given
    String refreshToken = "refresh-token";
    RefreshToken refreshTokenEntity = new RefreshToken();
    String username = "username";
    User expectedUser = new User();

    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(refreshTokenEntity);
    when(tokenService.getUsername(refreshToken)).thenReturn(username);
    when(getUserPort.getUserByUsername(username)).thenReturn(expectedUser);

    // when
    User actualUser = testSubject.checkUser(refreshToken);

    // then
    assertThat(actualUser).isEqualTo(expectedUser);

    verify(tokenService).verify(refreshToken);
  }

  @Test
  void checkUserByRefreshTokenThrowsExceptionWhenTokenWasNotFound() {
    // given
    String refreshToken = "refresh-token";

    when(refreshTokenPort.findByToken(refreshToken)).thenReturn(null);

    // when / then
    assertThatThrownBy(() -> testSubject.checkUser(refreshToken))
        .isInstanceOf(RefreshTokenNotFoundException.class);
  }

  @Test
  void getUserByRefreshTokenReturnsExpectedUser() {
    // given
    String refreshToken = "refresh-token";
    String username = "username";
    User expectedUser = new User();

    when(tokenService.getUsername(refreshToken)).thenReturn(username);
    when(getUserPort.getUserByUsername(username)).thenReturn(expectedUser);

    // when
    User actualUser = testSubject.getUser(refreshToken);

    // then
    assertThat(actualUser).isEqualTo(expectedUser);
  }
}
