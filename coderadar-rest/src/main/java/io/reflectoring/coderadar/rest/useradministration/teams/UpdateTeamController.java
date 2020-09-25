package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
public class UpdateTeamController implements AbstractBaseController {
  private final UpdateTeamUseCase updateTeamUseCase;

  @PostMapping(
      path = "/teams/{teamId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IdResponse> updateTeam(
      @RequestBody UpdateTeamCommand command, @PathVariable(name = "teamId") long teamId) {
    updateTeamUseCase.updateTeam(teamId, command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
