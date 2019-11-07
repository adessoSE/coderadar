package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class LoginUserController {
  private final LoginUserUseCase loginUserUseCase;

  @Autowired
  public LoginUserController(LoginUserUseCase loginUserUseCase) {
    this.loginUserUseCase = loginUserUseCase;
  }

  @PostMapping(path = "/user/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity login(@RequestBody @Validated LoginUserCommand command) {
    try {
      return new ResponseEntity<>(loginUserUseCase.login(command), HttpStatus.OK);
    } catch (AuthenticationException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }
  }
}
