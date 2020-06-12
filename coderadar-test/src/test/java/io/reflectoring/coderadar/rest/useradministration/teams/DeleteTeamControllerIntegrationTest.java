package io.reflectoring.coderadar.rest.useradministration.teams;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeleteTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private TeamRepository teamRepository;

  private UserEntity testUser;

  private TeamEntity teamEntity;

  @BeforeEach
  public void setUp() {
    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamRepository.save(teamEntity, 1);

    userRepository.deleteAll();
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);
  }

  @Test
  public void deleteTeamWithUsersSuccessfully() throws Exception {
    teamEntity.setMembers(Collections.singletonList(testUser));
    teamRepository.save(teamEntity, 1);

    mvc()
        .perform(delete("/api/teams/" + teamEntity.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(document("teams/delete"))
        .andReturn();

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertTrue(teams.isEmpty());
  }

  @Test
  public void deleteTeamWithNoUsersSuccessfully() throws Exception {
    mvc()
        .perform(delete("/api/teams/" + teamEntity.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk()); // TODO: Document

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertTrue(teams.isEmpty());
  }
}
