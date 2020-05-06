package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.IdResponse;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class CreateTeamController implements AbstractBaseController {
  private final CreateTeamUseCase createTeamUseCase;

  public CreateTeamController(CreateTeamUseCase createTeamUseCase) {
    this.createTeamUseCase = createTeamUseCase;
  }

  @PostMapping(
      path = "/teams",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamCommand command) {
    Long teamId = createTeamUseCase.createTeam(command);
    return new ResponseEntity<>(new IdResponse(teamId), HttpStatus.OK);
  }
}
