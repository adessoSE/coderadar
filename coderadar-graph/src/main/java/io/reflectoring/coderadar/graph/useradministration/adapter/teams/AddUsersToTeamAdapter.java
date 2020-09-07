package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.AddUsersToTeamPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddUsersToTeamAdapter implements AddUsersToTeamPort {
  private final TeamRepository teamRepository;

  @Override
  public void addUsersToTeam(long teamId, List<Long> userIds) {
    teamRepository.addUsersToTeam(teamId, userIds);
  }
}
