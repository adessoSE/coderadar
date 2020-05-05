package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordCommand;
import io.reflectoring.coderadar.useradministration.port.driver.password.ChangePasswordUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class ChangePasswordController implements AbstractBaseController {
  private final ChangePasswordUseCase changePasswordUseCase;

  public ChangePasswordController(ChangePasswordUseCase changePasswordUseCase) {
    this.changePasswordUseCase = changePasswordUseCase;
  }

  @PostMapping(
      path = "/user/password/change",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changePassword(@RequestBody @Validated ChangePasswordCommand command) {
    try {
      changePasswordUseCase.changePassword(command);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (AuthenticationException | RefreshTokenNotFoundException e) {
      return new ResponseEntity<>(
          new ErrorMessageResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }
}
