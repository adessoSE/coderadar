package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.domain.ProjectRole;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.useradministration.port.driven.AddTeamToProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddTeamToProjectAdapter implements AddTeamToProjectPort {
  private final TeamRepository teamRepository;

  @Override
  public void addTeamToProject(long projectId, long teamId, ProjectRole role) {
    teamRepository.addTeamToProject(projectId, teamId, role.getValue());
  }
}
