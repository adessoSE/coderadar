package io.reflectoring.coderadar.rest;

import static io.reflectoring.coderadar.rest.GetUserResponseMapper.mapUsers;

import io.reflectoring.coderadar.domain.Team;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import java.util.ArrayList;
import java.util.List;

public class GetTeamResponseMapper {
  private GetTeamResponseMapper() {}

  public static GetTeamResponse mapTeam(Team team) {
    return new GetTeamResponse(team.getId(), team.getName(), mapUsers(team.getMembers()));
  }

  public static List<GetTeamResponse> mapTeams(List<Team> teams) {
    List<GetTeamResponse> result = new ArrayList<>(teams.size());
    for (Team team : teams) {
      result.add(mapTeam(team));
    }
    return result;
  }
}
