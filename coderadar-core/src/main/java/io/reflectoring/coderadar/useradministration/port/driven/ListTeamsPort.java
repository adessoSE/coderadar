package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.Team;
import java.util.List;

public interface ListTeamsPort {
  /** @return All teams in the database. */
  List<Team> listTeams();
}
