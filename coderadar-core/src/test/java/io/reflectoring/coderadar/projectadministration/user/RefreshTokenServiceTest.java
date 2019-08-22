package io.reflectoring.coderadar.projectadministration.user;

import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.service.user.refresh.RefreshTokenService;
import io.reflectoring.coderadar.projectadministration.service.user.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
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
