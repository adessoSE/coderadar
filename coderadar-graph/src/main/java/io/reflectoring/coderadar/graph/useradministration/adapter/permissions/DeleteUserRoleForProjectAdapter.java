package io.reflectoring.coderadar.graph.useradministration.adapter.permissions;

import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserRoleForProjectPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserRoleForProjectAdapter implements DeleteUserRoleForProjectPort {

  private final UserRepository userRepository;

  public DeleteUserRoleForProjectAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void deleteRole(long projectId, long userId) {
    userRepository.removeUserRoleFromProject(projectId, userId);
  }
}
