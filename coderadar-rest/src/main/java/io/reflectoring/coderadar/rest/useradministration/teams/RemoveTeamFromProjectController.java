package io.reflectoring.coderadar.rest.useradministration.teams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.TeamNotAssignedException;
import io.reflectoring.coderadar.useradministration.port.driver.teams.RemoveTeamFromProjectUseCase;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
public class RemoveTeamFromProjectController implements AbstractBaseController {
  private final RemoveTeamFromProjectUseCase removeTeamFromProjectUseCase;
  private final AuthenticationService authenticationService;

  @DeleteMapping(path = "/projects/{projectId}/teams/{teamId}")
  public ResponseEntity<ErrorMessageResponse> removeTeamFromProject(
      @PathVariable long projectId, @PathVariable long teamId) {
    authenticationService.authenticateAdmin(projectId);
    try {
      removeTeamFromProjectUseCase.removeTeam(projectId, teamId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (TeamNotAssignedException e) {
      return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
