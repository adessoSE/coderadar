package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.TeamMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsForProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListTeamsForProjectAdapter implements ListTeamsForProjectPort {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper = new TeamMapper();

  public ListTeamsForProjectAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public List<Team> listTeamsForProject(long projectId) {
    return teamMapper.mapNodeEntities(teamRepository.listTeamsByProjectIdWithMembers(projectId));
  }
}