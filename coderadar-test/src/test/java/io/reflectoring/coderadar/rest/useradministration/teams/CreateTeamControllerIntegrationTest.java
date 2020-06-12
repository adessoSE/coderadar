package io.reflectoring.coderadar.rest.useradministration.teams;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CreateTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private TeamRepository teamRepository;

  private UserEntity testUser;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);
  }

  @Test
  public void createTeamWithUsersSuccessfully() throws Exception {
    CreateTeamCommand createTeamCommand = new CreateTeamCommand();
    createTeamCommand.setName("testTeam");
    createTeamCommand.setUserIds(Collections.singletonList(testUser.getId()));
    ConstrainedFields<CreateTeamCommand> fields = fields(CreateTeamCommand.class);

    mvc()
        .perform(
            post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createTeamCommand)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(
            document(
                "teams/create",
                requestFields(
                    fields.withPath("name").description("The name of the team"),
                    fields
                        .withPath("userIds")
                        .description("A list of user IDs to add to the newly created team"))))
        .andReturn();

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam", teams.get(0).getName());
    Assertions.assertEquals("username", teams.get(0).getMembers().get(0).getUsername());
  }

  @Test
  public void createTeamWithNoUsersSuccessfully() throws Exception {

    CreateTeamCommand createTeamCommand = new CreateTeamCommand();
    createTeamCommand.setName("testTeam");

    mvc()
        .perform(
            post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createTeamCommand)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn();

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam", teams.get(0).getName());
  }
}
