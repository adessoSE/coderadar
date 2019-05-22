package io.reflectoring.coderadar.core.projectadministration.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.service.user.refresh.RefreshTokenService;
import io.reflectoring.coderadar.core.projectadministration.service.user.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

class RefreshTokenServiceTest {

  private RefreshTokenPort refreshTokenPort = mock(RefreshTokenPort.class);
  private TokenService tokenService = mock(TokenService.class);
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);

  @InjectMocks private RefreshTokenService testSubject;

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
