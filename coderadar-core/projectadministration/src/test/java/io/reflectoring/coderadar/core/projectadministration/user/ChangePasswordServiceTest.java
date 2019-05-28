package io.reflectoring.coderadar.core.projectadministration.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.password.ChangePasswordService;
import io.reflectoring.coderadar.core.projectadministration.service.user.refresh.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ChangePasswordServiceTest {
  private ChangePasswordPort changePasswordPort = mock(ChangePasswordPort.class);
  private RefreshTokenService refreshTokenService = mock(RefreshTokenService.class);
  private RegisterUserPort registerUserPort = mock(RegisterUserPort.class);
  private RefreshTokenPort refreshTokenPort = mock(RefreshTokenPort.class);

  @Test
  void changePasswordSuccessfully() {
    ChangePasswordService testSubject =
        new ChangePasswordService(refreshTokenPort, refreshTokenService, changePasswordPort);

    Mockito.when(refreshTokenService.getUser(anyString())).thenReturn(new User());

    ChangePasswordCommand command = new ChangePasswordCommand("refresh token", "new password");
    testSubject.changePassword(command);

    Mockito.verify(changePasswordPort, Mockito.times(1)).changePassword(any());
  }
}
