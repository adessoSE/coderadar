package io.reflectoring.coderadar.rest.useradministration.permissions;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.RemoveUserFromProjectUseCase;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class RemoveUserFromProjectController implements AbstractBaseController {
  private final RemoveUserFromProjectUseCase removeUserFromProjectUseCase;
  private final AuthenticationService authenticationService;

  public RemoveUserFromProjectController(
      RemoveUserFromProjectUseCase removeUserFromProjectUseCase,
      AuthenticationService authenticationService) {
    this.removeUserFromProjectUseCase = removeUserFromProjectUseCase;
    this.authenticationService = authenticationService;
  }

  @DeleteMapping(path = "/projects/{projectId}/users/{userId}")
  public ResponseEntity<HttpStatus> removeUserRoleFromProject(
      @PathVariable long projectId, @PathVariable long userId) {
    authenticationService.authenticateAdmin(projectId);
    removeUserFromProjectUseCase.removeUserFromProject(projectId, userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
