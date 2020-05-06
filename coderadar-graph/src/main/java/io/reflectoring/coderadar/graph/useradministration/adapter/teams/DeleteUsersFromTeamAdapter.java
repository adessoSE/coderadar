package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.useradministration.port.driven.DeleteUsersFromTeamPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeleteUsersFromTeamAdapter implements DeleteUsersFromTeamPort {
  @Override
  public void deleteUsersFromTeam(long teamId, List<Long> userIds) {
    // TODO: implement
  }
}
