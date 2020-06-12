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

public class GetTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  private TeamEntity teamEntity;

  @BeforeEach
  public void setUp() {
    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamRepository.save(teamEntity, 1);
  }

  @Test
  public void getTeamSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/teams/" + teamEntity.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(document("teams/get"))
            .andReturn();

    GetTeamResponse response =
        fromJson(result.getResponse().getContentAsString(), GetTeamResponse.class);
    Assertions.assertEquals("testTeam", response.getName());
  }
}
