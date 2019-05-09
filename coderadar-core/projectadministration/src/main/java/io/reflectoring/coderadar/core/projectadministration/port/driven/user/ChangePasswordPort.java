package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

public interface ChangePasswordPort {
  void changePassword(String refreshToken, String newPassword);
}