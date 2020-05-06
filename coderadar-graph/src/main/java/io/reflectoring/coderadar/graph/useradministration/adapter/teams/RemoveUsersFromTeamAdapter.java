package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveUsersFromTeamPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RemoveUsersFromTeamAdapter implements RemoveUsersFromTeamPort {

  private final TeamRepository teamRepository;

  public RemoveUsersFromTeamAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public void removeUsersFromTeam(long teamId, List<Long> userIds) {
    teamRepository.deleteUsersFromTeam(teamId, userIds);
  }
}
