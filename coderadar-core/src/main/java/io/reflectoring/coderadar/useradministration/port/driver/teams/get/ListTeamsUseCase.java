package io.reflectoring.coderadar.useradministration.port.driver.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import java.util.List;

public interface ListTeamsUseCase {

  /** @return All teams in the database. */
  List<Team> listTeams();
}
