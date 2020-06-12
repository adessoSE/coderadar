package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.Team;

import java.util.List;

public interface ListTeamsForProjectPort {

  /**
   * @param projectId The id of the project.
   * @return All teams with access to this project.
   */
  List<Team> listTeamsForProject(long projectId);
}
