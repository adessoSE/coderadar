package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.rest.domain.GetTeamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

class GetTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  private TeamEntity teamEntity;

  @BeforeEach
  void setUp() {
    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamRepository.save(teamEntity, 1);
  }

  @Test
  void getTeamSuccessfully() throws Exception {
    MvcResult result =
        mvc()
            .perform(get("/api/teams/" + teamEntity.getId()))
            .andExpect(status().isOk())
            .andDo(document("teams/get"))
            .andReturn();

    GetTeamResponse response =
        fromJson(result.getResponse().getContentAsString(), GetTeamResponse.class);
    Assertions.assertEquals("testTeam", response.getName());
  }

  @Test
  void throwsExceptionWhenTeamDoesNotExist() throws Exception {
    MvcResult result =
        mvc().perform(get("/api/teams/1000")).andExpect(status().isNotFound()).andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Team with id 1000 not found.", errorMessage);
  }
}
