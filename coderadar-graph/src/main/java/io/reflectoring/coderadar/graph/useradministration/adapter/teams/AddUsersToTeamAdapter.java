package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.useradministration.port.driven.AddUsersToTeamPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddUsersToTeamAdapter implements AddUsersToTeamPort {
  @Override
  public void addUsersToTeam(long teamId, List<Long> userIds) {
    // TODO: implement
  }
}
