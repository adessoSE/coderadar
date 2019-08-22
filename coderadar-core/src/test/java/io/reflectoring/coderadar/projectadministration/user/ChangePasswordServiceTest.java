package io.reflectoring.coderadar.projectadministration.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.projectadministration.service.user.password.ChangePasswordService;
import io.reflectoring.coderadar.projectadministration.service.user.refresh.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChangePasswordServiceTest {

  @Mock private ChangePasswordPort changePasswordPort;

  @Mock private RefreshTokenService refreshTokenService;

  @Mock private RefreshTokenPort refreshTokenPort;

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
