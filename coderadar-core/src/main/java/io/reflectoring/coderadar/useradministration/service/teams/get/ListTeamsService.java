package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.port.driven.ListTeamsPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListTeamsUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListTeamsService implements ListTeamsUseCase {

  private final ListTeamsPort listTeamsPort;

  @Override
  public List<Team> listTeams() {
    return listTeamsPort.listTeams();
  }
}
