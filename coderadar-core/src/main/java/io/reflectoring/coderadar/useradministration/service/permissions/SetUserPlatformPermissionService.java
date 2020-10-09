package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserPlatformPermissionPort;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserPlatformPermissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SetUserPlatformPermissionService implements SetUserPlatformPermissionUseCase {

  private final SetUserPlatformPermissionPort setUserPlatformPermissionPort;
  private final GetUserPort getUserPort;

  @Override
  public void setUserPlatformPermission(long userId, boolean isAdmin) {
    if (getUserPort.existsById(userId)) {
      setUserPlatformPermissionPort.setUserPlatformPermission(userId, isAdmin);
    } else {
      throw new UserNotFoundException(userId);
    }
  }
}
