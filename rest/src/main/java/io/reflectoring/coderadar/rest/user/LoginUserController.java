package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginUserController {
  private final LoginUserUseCase loginUserUseCase;

  @Autowired
  public LoginUserController(LoginUserUseCase loginUserUseCase) {
    this.loginUserUseCase = loginUserUseCase;
  }

  @PostMapping(path = "/user/auth")
  public ResponseEntity login(@RequestBody @Validated LoginUserCommand command) {
    try {
      return new ResponseEntity<>(loginUserUseCase.login(command), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (AuthenticationException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
  }
}
