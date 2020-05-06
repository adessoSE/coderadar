package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.useradministration.port.driven.RemoveTeamFromProjectPort;
import org.springframework.stereotype.Service;

@Service
public class RemoveTeamFromProjectAdapter implements RemoveTeamFromProjectPort {
  @Override
  public void deleteTeam(long teamId) {
    // TODO: implement
  }
}
