package io.reflectoring.coderadar.rest.useradministration.teams;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

public class AddUsersToTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @Autowired private UserRepository userRepository;

  private TeamEntity teamEntity;
  private UserEntity testUser;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword("password1");
    userRepository.save(testUser);

    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamRepository.save(teamEntity, 1);
  }

  @Test
  public void addUsersToTeamSuccessfully() throws Exception {
    mvc()
        .perform(
            post("/api/teams/" + teamEntity.getId() + "/users")
                .content(toJson(new JsonListWrapper<>(Collections.singletonList(testUser.getId()))))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn(); // TODO: Document

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam", teams.get(0).getName());
    Assertions.assertEquals("username", teams.get(0).getMembers().get(0).getUsername());
  }
}
