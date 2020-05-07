package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.rest.GetTeamResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.domain.Team;
import io.reflectoring.coderadar.useradministration.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetTeamResponseMapperControllerTest {

  @Test
  public void testTeamResponseMapper() {
    List<Team> teams = new ArrayList<>();
    teams.add(
        new Team()
            .setName("testTeam1")
            .setId(1L)
            .setMembers(
                Collections.singletonList(
                    new User()
                        .setUsername("testUsername1")
                        .setId(2L)
                        .setPassword("testPassword1"))));

    teams.add(
        new Team()
            .setName("testTeam2")
            .setId(3L)
            .setMembers(
                Collections.singletonList(
                    new User()
                        .setUsername("testUsername2")
                        .setId(4L)
                        .setPassword("testPassword2"))));

    List<GetTeamResponse> responses = GetTeamResponseMapper.mapTeams(teams);

    Assertions.assertEquals(2L, responses.size());
    Assertions.assertEquals("testTeam1", responses.get(0).getName());
    Assertions.assertEquals(1L, responses.get(0).getId());
    Assertions.assertEquals(1L, responses.get(0).getMembers().size());
    Assertions.assertEquals(
        new GetUserResponse(2L, "testUsername1"), responses.get(0).getMembers().get(0));

    Assertions.assertEquals("testTeam2", responses.get(1).getName());
    Assertions.assertEquals(3L, responses.get(1).getId());
    Assertions.assertEquals(1L, responses.get(1).getMembers().size());
    Assertions.assertEquals(
        new GetUserResponse(4L, "testUsername2"), responses.get(1).getMembers().get(0));
  }

  @Test
  public void testTeamResponseSingleMapper() {
    Team team =
        new Team()
            .setName("testTeam1")
            .setId(1L)
            .setMembers(
                Collections.singletonList(
                    new User()
                        .setUsername("testUsername1")
                        .setId(2L)
                        .setPassword("testPassword1")));

    GetTeamResponse response = GetTeamResponseMapper.mapTeam(team);

    Assertions.assertEquals("testTeam1", response.getName());
    Assertions.assertEquals(1L, response.getId());
    Assertions.assertEquals(1L, response.getMembers().size());
    Assertions.assertEquals(new GetUserResponse(2L, "testUsername1"), response.getMembers().get(0));
  }
}
