package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteTeamPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeamAdapter implements DeleteTeamPort {
  private final TeamRepository teamRepository;

  @Override
  public void deleteTeam(long teamId) {
    teamRepository.deleteById(teamId);
  }
}
