package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.password.ChangePasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {
  private final ChangePasswordUseCase changePasswordUseCase;

  @Autowired
  public ChangePasswordController(ChangePasswordUseCase changePasswordUseCase) {
    this.changePasswordUseCase = changePasswordUseCase;
  }

  @PostMapping("/user/password/change")
  public ResponseEntity<String> changePassword(@RequestBody ChangePasswordCommand command) {
    changePasswordUseCase.changePassword(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
