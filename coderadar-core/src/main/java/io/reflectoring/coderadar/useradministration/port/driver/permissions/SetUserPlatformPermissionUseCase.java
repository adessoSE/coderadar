package io.reflectoring.coderadar.useradministration.port.driver.permissions;

public interface SetUserPlatformPermissionUseCase {

  void setUserPlatformPermission(long userId, boolean isAdmin);
}
