package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.User;

public interface ChangePasswordPort {
  void changePassword(User user);
}
