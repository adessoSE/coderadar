package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ChangePasswordControllerTest {

  @Mock private ChangePasswordUseCase changePasswordUseCase;
  @InjectMocks private ChangePasswordController testSubject;

  @Test
  void changePasswordSuccessfully() {
    Assertions.fail();
  }
}
