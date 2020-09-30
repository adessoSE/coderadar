package io.reflectoring.coderadar.useradministration.port.driven;

public interface SetUserPlatformPermissionPort {
  void setUserPlatformPermission(long userId, boolean isAdmin);
}
