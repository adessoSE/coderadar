package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

public interface ChangePasswordPort {
  void changePassword(Long id, String newPassword);
}
