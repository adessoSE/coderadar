package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ListTeamsForUserControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @Autowired private UserRepository userRepository;

  private TeamEntity teamEntity;
  private UserEntity testUser;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
    testUser = new UserEntity();
    testUser.setUsername("testUser");
    testUser.setPassword("password1");

    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamEntity.setMembers(Collections.singletonList(testUser));
    teamRepository.save(teamEntity, 1);
  }

  @Test
  public void listTeamsForProjectSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/users/" + testUser.getId() + "/teams"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn(); // TODO: Document

    GetTeamResponse[] teams =
        fromJson(result.getResponse().getContentAsString(), GetTeamResponse[].class);
    Assertions.assertEquals(1L, teams.length);
    Assertions.assertEquals("testTeam", teams[0].getName());
  }
}
