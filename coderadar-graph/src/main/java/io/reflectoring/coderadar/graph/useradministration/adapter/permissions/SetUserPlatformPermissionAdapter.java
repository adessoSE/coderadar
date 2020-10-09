package io.reflectoring.coderadar.graph.useradministration.adapter.permissions;

import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserPlatformPermissionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetUserPlatformPermissionAdapter implements SetUserPlatformPermissionPort {

  private final UserRepository userRepository;

  @Override
  public void setUserPlatformPermission(long userId, boolean isAdmin) {
    userRepository.setPlatformPermission(userId, isAdmin);
  }
}
