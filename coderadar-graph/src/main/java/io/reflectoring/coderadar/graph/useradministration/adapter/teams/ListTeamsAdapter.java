package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListTeamsAdapter implements ListTeamsPort {

  private final TeamRepository teamRepository;

  public ListTeamsAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public List<Team> listTeams() {
    return teamRepository.findAllWithMembers();
  }
}
