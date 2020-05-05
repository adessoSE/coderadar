package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteTeamPort;

public class DeleteTeamAdapter implements DeleteTeamPort {

  private final TeamRepository teamRepository;

  public DeleteTeamAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public void deleteTeam(long teamId) {
    teamRepository.deleteById(teamId);
  }
}
