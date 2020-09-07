package io.reflectoring.coderadar.graph.useradministration.adapter.permissions;

import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserRoleForProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetUserRoleForProjectAdapter implements SetUserRoleForProjectPort {
  private final UserRepository userRepository;

  @Override
  public void setRole(long projectId, long userId, ProjectRole role) {
    userRepository.setUserRoleForProject(projectId, userId, role.getValue());
  }
}
