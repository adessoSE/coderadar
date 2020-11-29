package io.reflectoring.coderadar.rest.useradministration.permissions;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.ProjectRoleJsonWrapper;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserRoleForProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SetUserRoleForProjectController implements AbstractBaseController {
  private final SetUserRoleForProjectUseCase setUserRoleForProjectUseCase;

  @PostMapping(
      path = "/projects/{projectId}/users/{userId}",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> setUserRoleForProject(
      @PathVariable long projectId,
      @PathVariable long userId,
      @RequestBody ProjectRoleJsonWrapper role) {
    setUserRoleForProjectUseCase.setRole(projectId, userId, role.getRole());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
