package io.reflectoring.coderadar.useradministration.port.driver.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;

import java.util.List;

public interface ListTeamsForUserUseCase {

  /**
   * @param userId The id of the user.
   * @return All teams the user is in.
   */
  List<Team> listTeamsForUser(long userId);
}
