package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.delete.DeleteUserUseCase;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class DeleteUserController implements AbstractBaseController {
  private final DeleteUserUseCase deleteUserUseCase;
  private final AuthenticationService authenticationService;

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") long userId) {
    authenticationService.authenticatePlatformAdmin();
    deleteUserUseCase.deleteUser(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
