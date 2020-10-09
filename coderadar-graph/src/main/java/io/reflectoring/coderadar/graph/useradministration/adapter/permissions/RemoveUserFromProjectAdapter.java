package io.reflectoring.coderadar.graph.useradministration.adapter.permissions;

import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveUserFromProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveUserFromProjectAdapter implements RemoveUserFromProjectPort {
  private final UserRepository userRepository;

  @Override
  public void removeUserFromProject(long projectId, long userId) {
    userRepository.removeUserRoleFromProject(projectId, userId);
  }
}
