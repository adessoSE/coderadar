package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.rest.GetTeamResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetTeamResponseMapperTest {

  @Test
  public void testTeamResponseMapper() {
    List<User> users = new ArrayList<>();
    users.add(new User().setUsername("testUser1").setId(2L).setPassword("testPassword1"));

    List<Team> teams = new ArrayList<>();
    teams.add(new Team().setId(1L).setMembers(users).setName("testTeam1"));

    List<GetTeamResponse> responses = GetTeamResponseMapper.mapTeams(teams);
    Assertions.assertEquals(1L, responses.size());

    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals("testTeam1", responses.get(0).getName());
    Assertions.assertEquals(1L, responses.get(0).getMembers().size());
    Assertions.assertEquals("testUser1", responses.get(0).getMembers().get(0).getUsername());
    Assertions.assertEquals(2L, responses.get(0).getMembers().get(0).getId());
  }

  @Test
  public void testTeamResponseSingleMapper() {
    List<User> users = new ArrayList<>();
    users.add(new User().setUsername("testUser1").setId(2L).setPassword("testPassword1"));
    Team team = new Team().setId(1L).setMembers(users).setName("testTeam1");
    GetTeamResponse response = GetTeamResponseMapper.mapTeam(team);

    Assertions.assertEquals(1L, response.getId());
    Assertions.assertEquals("testTeam1", response.getName());
    Assertions.assertEquals(1L, response.getMembers().size());
    Assertions.assertEquals("testUser1", response.getMembers().get(0).getUsername());
    Assertions.assertEquals(2L, response.getMembers().get(0).getId());
  }
}
