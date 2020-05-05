package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.AddTeamToProjectPort;
import org.springframework.stereotype.Service;

@Service
public class AddTeamToProjectAdapter implements AddTeamToProjectPort {

  private final TeamRepository teamRepository;

  public AddTeamToProjectAdapter(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public void addTeamToProject(long projectId, long teamId, ProjectRole role) {
    teamRepository.addTeamToProject(projectId, teamId, role.getValue());
  }
}
