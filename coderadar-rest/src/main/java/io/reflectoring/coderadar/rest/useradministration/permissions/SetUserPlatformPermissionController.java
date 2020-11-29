package io.reflectoring.coderadar.rest.useradministration.permissions;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserPlatformPermissionUseCase;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SetUserPlatformPermissionController implements AbstractBaseController {

  private final SetUserPlatformPermissionUseCase setUserPlatformPermissionUseCase;

  @PostMapping(value = "/users/{userId}/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> setUserPlatformPermission(
      @PathVariable("userId") long userId, @PathParam("isAdmin") boolean isAdmin) {
    setUserPlatformPermissionUseCase.setUserPlatformPermission(userId, isAdmin);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
