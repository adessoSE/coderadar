package io.reflectoring.coderadar.rest.useradministration.teams;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.JsonListWrapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class RemoveUsersFromTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @Autowired private UserRepository userRepository;

  private TeamEntity teamEntity;
  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword("password1");
    userRepository.save(testUser);

    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamEntity.setMembers(Collections.singletonList(testUser));
    teamRepository.save(teamEntity, 1);
  }

  @Test
  void removeUsersFromTeamSuccessfully() throws Exception {
    ConstrainedFields<JsonListWrapper> fields = fields(JsonListWrapper.class);

    mvc()
        .perform(
            delete("/api/teams/" + teamEntity.getId() + "/users")
                .content(toJson(new JsonListWrapper<>(Collections.singletonList(testUser.getId()))))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            document(
                "teams/remove/user",
                requestFields(
                    fields.withPath("elements").description("A list containing user IDs"))))
        .andReturn();

    List<TeamEntity> teams = teamRepository.listTeamsByUserId(testUser.getId());
    Assertions.assertEquals(0, teams.size());
  }
}
