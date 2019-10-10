package io.reflectoring.coderadar.projectadministration.user;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.projectadministration.service.user.password.ChangePasswordService;
import io.reflectoring.coderadar.projectadministration.service.user.refresh.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChangePasswordServiceTest {

  @Mock private ChangePasswordPort changePasswordPortMock;

  @Mock private RefreshTokenService refreshTokenServiceMock;

  @Mock private RefreshTokenPort refreshTokenPortMock;

  private ChangePasswordService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new ChangePasswordService(
            refreshTokenPortMock, refreshTokenServiceMock, changePasswordPortMock);
  }

  @Test
  void changePasswordSuccessfully(@Mock User userToUpdateMock) {
    // given
    String refreshToken = "refresh-token";
    String newPassword = "new-password";

    ChangePasswordCommand command = new ChangePasswordCommand(refreshToken, newPassword);

    when(refreshTokenServiceMock.getUser(refreshToken)).thenReturn(userToUpdateMock);

    // when
    testSubject.changePassword(command);

    // then
    verify(userToUpdateMock).setPassword(anyString());
    verify(changePasswordPortMock).changePassword(userToUpdateMock);
    verify(refreshTokenPortMock).deleteByUser(userToUpdateMock);
  }
}
