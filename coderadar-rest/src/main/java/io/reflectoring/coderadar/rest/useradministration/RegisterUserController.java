package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.useradministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterUserController implements AbstractBaseController {
  private final RegisterUserUseCase registerUserUseCase;

  @PostMapping(
      path = "/user/registration",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> register(@RequestBody @Validated RegisterUserCommand command) {
    try {
      return new ResponseEntity<>(
          new IdResponse(registerUserUseCase.register(command)), HttpStatus.CREATED);
    } catch (UsernameAlreadyInUseException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
  }
}
