package io.reflectoring.coderadar.rest.useradministration;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListUsersForProjectUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.reflectoring.coderadar.rest.GetUserResponseMapper.mapUsers;

@RestController
@Transactional
public class ListUsersForProjectController implements AbstractBaseController {
  private final ListUsersForProjectUseCase listUsersForProjectUseCase;

  public ListUsersForProjectController(ListUsersForProjectUseCase listUsersForProjectUseCase) {
    this.listUsersForProjectUseCase = listUsersForProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetUserResponse>> listTeamsForUser(@PathVariable long projectId) {
    List<User> users = listUsersForProjectUseCase.listUsers(projectId);
    return new ResponseEntity<>(mapUsers(users), HttpStatus.OK);
  }
}
