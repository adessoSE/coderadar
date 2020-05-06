package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsForProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListTeamsForProjectAdapter implements ListTeamsForProjectPort {
  @Override
  public List<Team> listTeamsForProject(long projectId) {
    return null; // TODO: implement
  }
}
