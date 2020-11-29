package io.reflectoring.coderadar.rest.useradministration.permissions;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.UserNotAssignedException;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.RemoveUserFromProjectUseCase;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RemoveUserFromProjectController implements AbstractBaseController {
  private final RemoveUserFromProjectUseCase removeUserFromProjectUseCase;
  private final AuthenticationService authenticationService;

  @DeleteMapping(path = "/projects/{projectId}/users/{userId}")
  public ResponseEntity<ErrorMessageResponse> removeUserRoleFromProject(
      @PathVariable long projectId, @PathVariable long userId) {
    authenticationService.authenticateAdmin(projectId);
    try {
      removeUserFromProjectUseCase.removeUserFromProject(projectId, userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (UserNotAssignedException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
