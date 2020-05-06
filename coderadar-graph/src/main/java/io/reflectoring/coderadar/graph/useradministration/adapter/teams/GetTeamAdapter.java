package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.TeamMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import org.springframework.stereotype.Service;

@Service
public class GetTeamAdapter implements GetTeamPort {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper = new TeamMapper();

  public GetTeamAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public Team get(long teamId) {
    return teamMapper.mapGraphObject(teamRepository.findByIdWithMembers(teamId));
  }

  @Override
  public boolean existsById(long teamId) {
    return teamRepository.existsById(teamId);
  }

  @Override
  public boolean existsByName(String name) {
    return teamRepository.existsByName(name);
  }
}
