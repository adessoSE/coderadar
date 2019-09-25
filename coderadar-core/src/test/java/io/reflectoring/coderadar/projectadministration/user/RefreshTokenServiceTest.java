package io.reflectoring.coderadar.projectadministration.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.projectadministration.AccessTokenNotExpiredException;
import io.reflectoring.coderadar.projectadministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.projectadministration.service.user.refresh.RefreshTokenService;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

  @Mock private RefreshTokenPort refreshTokenPort;

  @Mock private TokenService tokenService;

  @Mock private LoadUserPort loadUserPort;

  private RefreshTokenService testSubject;

  @BeforeEach
  void setUp() {
    testSubject = new RefreshTokenService(loadUserPort, refreshTokenPort, tokenService);
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
    when(loadUserPort.loadUserByUsername(username)).thenReturn(user);
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
    when(loadUserPort.loadUserByUsername(username)).thenReturn(null);

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
    when(loadUserPort.loadUserByUsername(username)).thenReturn(user);
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
    when(loadUserPort.loadUserByUsername(username)).thenReturn(null);

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
    when(loadUserPort.loadUserByUsername(username)).thenReturn(expectedUser);

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
    when(loadUserPort.loadUserByUsername(username)).thenReturn(expectedUser);

    // when
    User actualUser = testSubject.getUser(refreshToken);

    // then
    assertThat(actualUser).isEqualTo(expectedUser);
  }
}
