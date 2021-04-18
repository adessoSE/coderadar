package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.domain.Team;
import io.reflectoring.coderadar.graph.useradministration.TeamMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsForUserPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTeamsForUserAdapter implements ListTeamsForUserPort {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper = new TeamMapper();

  @Override
  public List<Team> listTeamsForUser(long userId) {
    return teamMapper.mapNodeEntities(teamRepository.listTeamsByUserId(userId));
  }
}
