package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.GetTeamResponseMapper.mapTeams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForProjectUseCase;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListTeamsForProjectController implements AbstractBaseController {
  private final ListTeamsForProjectUseCase listTeamsForProjectUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(path = "/projects/{projectId}/teams", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetTeamResponse>> listTeamsForProject(@PathVariable long projectId) {
    authenticationService.authenticateMember(projectId);
    List<Team> teams = listTeamsForProjectUseCase.listTeamsForProject(projectId);
    return new ResponseEntity<>(mapTeams(teams), HttpStatus.OK);
  }
}
