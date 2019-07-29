package io.reflectoring.coderadar.projectadministration.user;

import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.service.user.refresh.RefreshTokenService;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

class RefreshTokenServiceTest {

  private RefreshTokenPort refreshTokenPort = mock(RefreshTokenPort.class);
  private TokenService tokenService = mock(TokenService.class);
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);

  private RefreshTokenService testSubject;

  @BeforeEach
  public void setUp() {
    testSubject = new RefreshTokenService(loadUserPort, refreshTokenPort, tokenService);
  }

  // TODO: Service tests
  /*  @Test
  void returnsNewAccessToken() {
    when(tokenService.isExpired(anyString())).thenReturn(true);
    when(refreshTokenPort.findByToken(anyString())).thenReturn(new RefreshToken());

    String refreshToken = "refreshToken";
    String newAccessToken = "newAccessToken";

    RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand("AAAA", refreshToken);
    String responseToken = testSubject.refreshToken(refreshTokenCommand);

    Assertions.assertEquals(newAccessToken, responseToken);
  }*/
}
