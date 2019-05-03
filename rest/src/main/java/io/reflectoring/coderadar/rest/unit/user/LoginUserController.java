package io.reflectoring.coderadar.rest.unit.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class LoginUserController {
  private final LoginUserUseCase loginUserUseCase;

  @Autowired
  public LoginUserController(LoginUserUseCase loginUserUseCase) {
    this.loginUserUseCase = loginUserUseCase;
  }

  @PostMapping("/auth")
  public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserCommand command) {
    return new ResponseEntity<>(loginUserUseCase.login(command), HttpStatus.OK);
  }
}
