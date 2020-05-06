package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUsersFromTeamPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeleteUsersFromTeamAdapter implements DeleteUsersFromTeamPort {

  private final TeamRepository teamRepository;

  public DeleteUsersFromTeamAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public void deleteUsersFromTeam(long teamId, List<Long> userIds) {
    teamRepository.deleteUsersFromTeam(teamId, userIds);
  }
}
