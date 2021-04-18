package io.reflectoring.coderadar.useradministration.port.driver.get;

import io.reflectoring.coderadar.domain.User;
import java.util.List;

public interface ListUsersUseCase {

  /** @return All users in the database. */
  List<User> listUsers();
}
