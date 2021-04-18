package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.domain.Team;
import io.reflectoring.coderadar.graph.useradministration.TeamMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTeamAdapter implements GetTeamPort {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper = new TeamMapper();

  @Override
  public Team get(long teamId) {
    return teamMapper.mapGraphObject(teamRepository.findByIdWithMembers(teamId));
  }

  @Override
  public Team getByName(String name) {
    return teamMapper.mapGraphObject(teamRepository.findByName(name));
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
