package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.password.ChangePasswordService;
import io.reflectoring.coderadar.core.projectadministration.service.user.refresh.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class ChangePasswordServiceTest {
  @Mock private ChangePasswordPort changePasswordPort;
  @Mock private RefreshTokenService refreshTokenService;
  @Mock private RegisterUserPort registerUserPort;
  @Mock private RefreshTokenPort refreshTokenPort;
  @InjectMocks private ChangePasswordService testSubject;

  @Test
  void changePasswordSuccessfully() {
    Mockito.when(refreshTokenService.getUser(anyString())).thenReturn(new User());

    ChangePasswordCommand command = new ChangePasswordCommand("refresh token", "new password");
    testSubject.changePassword(command);

    Mockito.verify(changePasswordPort, Mockito.times(1))
        .changePassword(any());
  }
}
