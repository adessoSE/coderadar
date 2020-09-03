package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.graph.useradministration.TeamMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.useradministration.domain.Team;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TeamMapperTest {
  private final TeamMapper teamMapper = new TeamMapper();

  @Test
  void testMapDomainObject() {
    Team testTeam = new Team().setId(1L).setName("testTeam");

    TeamEntity result = teamMapper.mapDomainObject(testTeam);
    Assertions.assertEquals("testTeam", result.getName());
    Assertions.assertNull(result.getId());
  }

  @Test
  void testMapGraphObject() {
    TeamEntity testModule =
        new TeamEntity()
            .setId(1L)
            .setName("testTeam")
            .setMembers(
                Collections.singletonList(new UserEntity().setId(2L).setUsername("testUsername")));

    Team result = teamMapper.mapGraphObject(testModule);
    Assertions.assertEquals("testTeam", result.getName());
    Assertions.assertEquals(1L, result.getId());
    Assertions.assertEquals(1L, result.getMembers().size());
    Assertions.assertEquals(2L, result.getMembers().get(0).getId());
    Assertions.assertEquals("testUsername", result.getMembers().get(0).getUsername());
  }
}
