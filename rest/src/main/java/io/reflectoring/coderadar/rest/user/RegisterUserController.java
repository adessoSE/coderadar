package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserUseCase;
import io.reflectoring.coderadar.rest.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.IdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user/registration")
public class RegisterUserController {
  private final RegisterUserUseCase registerUserUseCase;

  @Autowired
  public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
  }

  @PostMapping
  public ResponseEntity register(@RequestBody @Validated RegisterUserCommand command) {
    try {
      return new ResponseEntity<>(
          new IdResponse(registerUserUseCase.register(command)), HttpStatus.CREATED);
    } catch (UsernameAlreadyInUseException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
