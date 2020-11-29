package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.GetTeamResponseMapper.mapTeams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListTeamsController implements AbstractBaseController {
  private final ListTeamsUseCase listTeamsUseCase;

  @GetMapping(path = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetTeamResponse>> listTeams() {
    List<Team> teams = listTeamsUseCase.listTeams();
    return new ResponseEntity<>(mapTeams(teams), HttpStatus.OK);
  }
}
