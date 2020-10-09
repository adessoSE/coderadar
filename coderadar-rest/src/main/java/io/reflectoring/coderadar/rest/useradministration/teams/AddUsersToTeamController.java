package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.JsonListWrapper;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddUsersToTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
public class AddUsersToTeamController implements AbstractBaseController {
  private final AddUsersToTeamUseCase addUsersToTeamUseCase;

  @PostMapping(path = "/teams/{teamId}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> addUsersToTeam(
      @PathVariable long teamId, @RequestBody @Validated JsonListWrapper<Long> userIds) {
    addUsersToTeamUseCase.addUsersToTeam(teamId, userIds.getElements());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
