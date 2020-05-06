package io.reflectoring.coderadar.graph.useradministration.adapter.permissions;

import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserRoleForProjectPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserRoleForProjectAdapter implements DeleteUserRoleForProjectPort {
  @Override
  public void deleteRole(long projectId, long userId) {
    // TODO: implement
  }
}
