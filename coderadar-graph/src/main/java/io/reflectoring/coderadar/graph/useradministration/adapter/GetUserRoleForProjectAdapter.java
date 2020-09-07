package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserRoleForProjectPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserRoleForProjectAdapter implements GetUserRoleForProjectPort {
  private final UserRepository userRepository;

  @Override
  public ProjectRole getRole(long projectId, long userId) {
    List<String> rolesInTeams = userRepository.getUserRolesForProjectInTeams(projectId, userId);
    String roleInProject = userRepository.getUserRoleForProject(projectId, userId);
    if (roleInProject != null) {
      rolesInTeams.add(roleInProject);
    }
    if (!rolesInTeams.isEmpty()) {
      rolesInTeams.sort(String::compareTo);
      return ProjectRole.valueOf(rolesInTeams.get(0).toUpperCase());
    } else {
      return null;
    }
  }
}
