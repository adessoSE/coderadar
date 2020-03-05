package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.rest.domain.LoadUserResponse;
import io.reflectoring.coderadar.useradministration.port.driver.load.LoadUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class LoadUserController {
  private final LoadUserUseCase loadUserUseCase;

  public LoadUserController(LoadUserUseCase loadUserUseCase) {
    this.loadUserUseCase = loadUserUseCase;
  }

  @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoadUserResponse> loadUser(@PathVariable Long userId) {
    return new ResponseEntity<>(
        new LoadUserResponse(userId, loadUserUseCase.loadUser(userId).getUsername()),
        HttpStatus.OK);
  }
}
