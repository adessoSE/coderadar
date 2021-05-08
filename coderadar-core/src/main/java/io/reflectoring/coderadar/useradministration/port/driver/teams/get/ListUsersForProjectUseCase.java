package io.reflectoring.coderadar.useradministration.port.driver.teams.get;

import io.reflectoring.coderadar.domain.User;
import java.util.List;

public interface ListUsersForProjectUseCase {

  /**
   * @param projectId The project id.
   * @return All users that have access to this project.
   */
  List<User> listUsers(long projectId);
}
