package io.reflectoring.coderadar.graph.useradministration.adapter.permissions;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserRoleForProjectPort;
import org.springframework.stereotype.Service;

@Service
public class SetUserRoleForProjectAdapter implements SetUserRoleForProjectPort {
  @Override
  public void setRole(long projectId, long userId, ProjectRole role) {
    // TODO: implement
  }
}
