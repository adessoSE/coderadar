package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteTeamUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class DeleteTeamController implements AbstractBaseController {
  private final DeleteTeamUseCase deleteTeamUseCase;

  public DeleteTeamController(DeleteTeamUseCase deleteTeamUseCase) {
    this.deleteTeamUseCase = deleteTeamUseCase;
  }

  @DeleteMapping(path = "/teams/{teamId}")
  public ResponseEntity<HttpStatus> deleteTeam(@PathVariable long teamId) {
    deleteTeamUseCase.deleteTeam(teamId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
