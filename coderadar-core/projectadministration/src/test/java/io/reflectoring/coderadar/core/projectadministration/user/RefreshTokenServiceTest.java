package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.refresh.RefreshTokenCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.RefreshTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RefreshTokenServiceTest {
  @Mock private RefreshTokenPort refreshTokenPort;
  @InjectMocks private RefreshTokenService testSubject;

  @Test
  void returnsNewAccessToken() {
    String refreshToken = "refreshToken";
    String newAccessToken = "newAccessToken";
    Mockito.when(refreshTokenPort.createAccessToken(refreshToken)).thenReturn(newAccessToken);

    RefreshTokenCommand command = new RefreshTokenCommand("access token", refreshToken);
    String responseToken = testSubject.refreshToken(command);

    Assertions.assertEquals(newAccessToken, responseToken);
  }
}
