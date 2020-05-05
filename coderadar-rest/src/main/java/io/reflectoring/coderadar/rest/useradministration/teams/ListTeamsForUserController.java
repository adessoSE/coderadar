package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.GetTeamResponseMapper.mapTeams;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsForUserUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListTeamsForUserController implements AbstractBaseController {
  private final ListTeamsForUserUseCase listTeamsForUserUseCase;

  public ListTeamsForUserController(ListTeamsForUserUseCase listTeamsForUserUseCase) {
    this.listTeamsForUserUseCase = listTeamsForUserUseCase;
  }

  @GetMapping(path = "/users/{userId}/teams")
  public ResponseEntity<List<GetTeamResponse>> listTeamsForUser(@PathVariable long userId) {
    List<Team> teams = listTeamsForUserUseCase.listTeamsForUser(userId);
    return new ResponseEntity<>(mapTeams(teams), HttpStatus.OK);
  }
}
