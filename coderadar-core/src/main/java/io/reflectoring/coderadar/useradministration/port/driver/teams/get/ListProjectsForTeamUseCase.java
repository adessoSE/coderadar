package io.reflectoring.coderadar.useradministration.port.driver.teams.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;
import java.util.List;

public interface ListProjectsForTeamUseCase {

  /**
   * @param teamId The id of the team.
   * @return All projects the team has access to.
   */
  List<Project> listProjects(long teamId);

  /**
   * @param teamId The id of the team.
   * @param userId The id of the user.
   * @return All projects the team has access to and the roles the team has for the project.
   */
  List<ProjectWithRoles> listProjectsWithUserRoles(long teamId, long userId);
}
