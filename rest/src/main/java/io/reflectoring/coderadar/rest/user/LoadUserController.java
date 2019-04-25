package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoadUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class LoadUserController {
  private final LoadUserUseCase loadUserUseCase;

  @Autowired
  public LoadUserController(LoadUserUseCase loadUserUseCase) {
    this.loadUserUseCase = loadUserUseCase;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> loadUser(@PathVariable Long userId) {
    return new ResponseEntity<>(loadUserUseCase.loadUser(userId), HttpStatus.OK);
  }
}
