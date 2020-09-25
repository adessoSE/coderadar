package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.Team;

public interface GetTeamPort {
  /**
   * @param teamId The team id.
   * @return The team with the given id.
   */
  Team get(long teamId);

  /**
   * @param name The team name.
   * @return The team with the given name.
   */
  Team getByName(String name);

  /**
   * @param teamId The id of the team.
   * @return True if a team with the given id exists.
   */
  boolean existsById(long teamId);

  /**
   * @param name The name of the team.
   * @return True if a team with the given name exists.
   */
  boolean existsByName(String name);
}
