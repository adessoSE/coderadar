package io.reflectoring.coderadar.projectadministration.port.driven.user;

import io.reflectoring.coderadar.projectadministration.domain.User;

public interface ChangePasswordPort {
  void changePassword(User user);
}
