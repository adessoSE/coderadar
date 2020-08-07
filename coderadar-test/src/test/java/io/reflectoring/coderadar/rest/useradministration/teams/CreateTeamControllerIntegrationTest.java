package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class CreateTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private TeamRepository teamRepository;

  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);
  }

  @Test
  void createTeamWithUsersSuccessfully() throws Exception {
    CreateTeamCommand createTeamCommand = new CreateTeamCommand();
    createTeamCommand.setName("testTeam");
    createTeamCommand.setUserIds(Collections.singletonList(testUser.getId()));
    ConstrainedFields<CreateTeamCommand> fields = fields(CreateTeamCommand.class);

    mvc()
        .perform(
            post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createTeamCommand)))
        .andExpect(status().isCreated())
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
  void createTeamWithNoUsersSuccessfully() throws Exception {
    CreateTeamCommand createTeamCommand = new CreateTeamCommand();
    createTeamCommand.setName("testTeam");

    mvc()
        .perform(
            post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createTeamCommand)))
        .andExpect(status().isCreated())
        .andReturn();

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam", teams.get(0).getName());
  }

  @Test
  void throwsExceptionWhenTeamAlreadyExists() throws Exception {
    CreateTeamCommand createTeamCommand = new CreateTeamCommand();
    createTeamCommand.setName("testTeam");

    // create team
    mvc()
        .perform(
            post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createTeamCommand)))
        .andExpect(status().isCreated())
        .andReturn();

    // try to create team with the same name
    MvcResult result =
        mvc()
            .perform(
                post("/api/teams")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(createTeamCommand)))
            .andExpect(status().isConflict())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Team with name testTeam already exists!", errorMessage);
  }

  @Test
  void throwsExceptionWhenUserNotFound() throws Exception {
    CreateTeamCommand command = new CreateTeamCommand();
    command.setName("testTeam");
    command.setUserIds(Collections.singletonList(100L));

    MvcResult result =
        mvc()
            .perform(
                post("/api/teams").contentType(MediaType.APPLICATION_JSON).content(toJson(command)))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("User with id 100 not found.", errorMessage);
  }
}
