package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveTeamFromProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveTeamFromProjectAdapter implements RemoveTeamFromProjectPort {
  private final TeamRepository teamRepository;

  @Override
  public void removeTeam(long projectId, long teamId) {
    teamRepository.removeTeamFromProject(projectId, teamId);
  }
}
