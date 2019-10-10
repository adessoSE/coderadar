package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.password.ChangePasswordUseCase;
import io.reflectoring.coderadar.rest.user.ChangePasswordController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;

class ChangePasswordControllerTest {

  private ChangePasswordUseCase changePasswordUseCase = mock(ChangePasswordUseCase.class);

  @Test
  void changePasswordSuccessfully() {
    ChangePasswordController testSubject = new ChangePasswordController(changePasswordUseCase);
    ChangePasswordCommand command = new ChangePasswordCommand("refreshToken", "newPassword");

    ResponseEntity<String> responseEntity = testSubject.changePassword(command);

    Mockito.verify(changePasswordUseCase, Mockito.times(1)).changePassword(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
