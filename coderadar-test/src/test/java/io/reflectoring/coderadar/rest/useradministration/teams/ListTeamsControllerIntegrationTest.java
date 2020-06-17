package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ListTeamsControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @BeforeEach
  void setUp() {
    TeamEntity teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamRepository.save(teamEntity, 1);
  }

  @Test
  void listTeamsSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/teams/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(document("teams/list"))
            .andReturn();

    GetTeamResponse[] response =
        fromJson(result.getResponse().getContentAsString(), GetTeamResponse[].class);
    Assertions.assertEquals(1, response.length);
    Assertions.assertEquals("testTeam", response[0].getName());
  }
}
