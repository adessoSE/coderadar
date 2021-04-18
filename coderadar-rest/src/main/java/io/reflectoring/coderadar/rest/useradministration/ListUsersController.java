package io.reflectoring.coderadar.rest.useradministration;

import static io.reflectoring.coderadar.rest.GetUserResponseMapper.mapUsers;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListUsersUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListUsersController implements AbstractBaseController {
  private final ListUsersUseCase listUsersUseCase;

  @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetUserResponse>> listUsers() {
    List<User> users = listUsersUseCase.listUsers();
    return new ResponseEntity<>(mapUsers(users), HttpStatus.OK);
  }
}
