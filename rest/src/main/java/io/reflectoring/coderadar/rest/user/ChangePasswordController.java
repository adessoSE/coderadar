package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.ChangePasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class ChangePasswordController {
  private final ChangePasswordUseCase changePasswordUseCase;

  @Autowired
  public ChangePasswordController(ChangePasswordUseCase changePasswordUseCase) {
    this.changePasswordUseCase = changePasswordUseCase;
  }

  @PostMapping("/password/change")
  public ResponseEntity<String> changePassword(@RequestBody ChangePasswordCommand command) {
    changePasswordUseCase.changePassword(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
