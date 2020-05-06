package io.reflectoring.coderadar.rest.unit.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.rest.useradministration.ChangePasswordController;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordCommand;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ChangePasswordControllerTest {

  private final ChangePasswordUseCase changePasswordUseCase = mock(ChangePasswordUseCase.class);

  @Test
  void changePasswordSuccessfully() {
    ChangePasswordController testSubject = new ChangePasswordController(changePasswordUseCase);
    ChangePasswordCommand command = new ChangePasswordCommand("refreshToken", "newPassword");

    ResponseEntity<String> responseEntity = testSubject.changePassword(command);

    Mockito.verify(changePasswordUseCase, Mockito.times(1)).changePassword(command);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
