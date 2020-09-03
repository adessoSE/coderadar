package io.reflectoring.coderadar.useradministration.port.driver.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import java.util.List;

public interface ListTeamsForProjectUseCase {

  /**
   * @param projectId The id of the project.
   * @return All teams with access to this project.
   */
  List<Team> listTeamsForProject(long projectId);
}
