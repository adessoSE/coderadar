package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.RegisterUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class RegisterUserController {
  private final RegisterUserUseCase registerUserUseCase;

  @Autowired
  public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
  }

  @PostMapping("/registration")
  public ResponseEntity<Long> register(@RequestBody RegisterUserCommand command) {
    return new ResponseEntity<>(registerUserUseCase.register(command), HttpStatus.CREATED);
  }
}
