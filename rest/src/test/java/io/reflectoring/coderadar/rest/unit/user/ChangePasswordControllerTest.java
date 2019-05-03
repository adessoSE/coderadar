package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ChangePasswordControllerTest {

  @Mock private ChangePasswordUseCase changePasswordUseCase;
  @InjectMocks private ChangePasswordController testSubject;

  @Test
  void changePasswordSuccessfully() {
    ChangePasswordCommand command = new ChangePasswordCommand("refreshToken", "newPassword");

    ResponseEntity<String> responseEntity = testSubject.changePassword(command);

    Mockito.verify(changePasswordUseCase, Mockito.times(1)).changePassword(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
