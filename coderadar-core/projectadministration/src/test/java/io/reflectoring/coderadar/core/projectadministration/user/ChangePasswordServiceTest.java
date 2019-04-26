package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.ChangePasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ChangePasswordServiceTest {
  @Mock private ChangePasswordPort changePasswordPort;
  private ChangePasswordService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new ChangePasswordService(changePasswordPort);
  }

  @Test
  void changePasswordSuccessfully() {
    ChangePasswordCommand command = new ChangePasswordCommand("refresh token", "new password");
    testSubject.changePassword(command);

    Mockito.verify(changePasswordPort, Mockito.times(1))
        .changePassword("refresh token", "new password");
  }
}
