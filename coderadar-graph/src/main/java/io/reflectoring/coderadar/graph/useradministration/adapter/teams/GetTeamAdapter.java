package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import org.springframework.stereotype.Service;

@Service
public class GetTeamAdapter implements GetTeamPort {
  @Override
  public Team get(long teamId) {
    return null; // TODO: implement
  }

  @Override
  public boolean existsById(long teamId) {
    return false; // TODO: implement
  }

  @Override
  public boolean existsByName(String name) {
    return false; // TODO: implement
  }
}
