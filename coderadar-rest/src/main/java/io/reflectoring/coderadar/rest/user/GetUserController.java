package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.port.driver.get.GetUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetUserController implements AbstractBaseController {
  private final GetUserUseCase getUserUseCase;

  public GetUserController(GetUserUseCase getUserUseCase) {
    this.getUserUseCase = getUserUseCase;
  }

  @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetUserResponse> loadUser(@PathVariable long userId) {
    return new ResponseEntity<>(
        new GetUserResponse(userId, getUserUseCase.getUser(userId).getUsername()), HttpStatus.OK);
  }
}
