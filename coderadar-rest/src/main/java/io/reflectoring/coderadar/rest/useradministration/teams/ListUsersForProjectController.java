package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.GetUserResponseMapper.mapUsers;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersForProjectPort;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListUsersForProjectController implements AbstractBaseController {
  private final ListUsersForProjectPort listUsersForProjectPort;

  public ListUsersForProjectController(ListUsersForProjectPort listUsersForProjectPort) {
    this.listUsersForProjectPort = listUsersForProjectPort;
  }

  @GetMapping(path = "/projects/{projectId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetUserResponse>> listTeamsForUser(@PathVariable long projectId) {
    List<User> users = listUsersForProjectPort.listUsers(projectId);
    return new ResponseEntity<>(mapUsers(users), HttpStatus.OK);
  }
}
