package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.useradministration.port.driver.load.LoadUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public LoadUserController(LoadUserUseCase loadUserUseCase) {
    this.loadUserUseCase = loadUserUseCase;
  }

  @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity loadUser(@PathVariable Long userId) {
    return new ResponseEntity<>(loadUserUseCase.loadUser(userId), HttpStatus.OK);
  }
}
