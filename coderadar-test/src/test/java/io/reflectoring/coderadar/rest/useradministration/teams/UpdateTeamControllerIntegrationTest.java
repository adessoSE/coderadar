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
import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamCommand;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import io.reflectoring.coderadar.useradministration.service.teams.create.CreateTeamService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class UpdateTeamControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private TeamRepository teamRepository;

  @Autowired private CreateTeamService createTeamService;

  private UserEntity testUser;
  private UserEntity testUser2;

  private long teamId;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setUsername("username");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);

    testUser2 = new UserEntity();
    testUser2.setUsername("username2");
    testUser2.setPassword(PasswordUtil.hash("password2"));
    userRepository.save(testUser2);

    CreateTeamCommand createTeamCommand = new CreateTeamCommand();
    createTeamCommand.setName("testTeam");

    teamId = createTeamService.createTeam(createTeamCommand);
  }

  @Test
  void updateTeamWithUsersSuccessfully() throws Exception {

    // Add a new user to the team
    teamRepository.addUsersToTeam(teamId, Collections.singletonList(testUser2.getId()));

    UpdateTeamCommand updateTeamCommand = new UpdateTeamCommand();
    updateTeamCommand.setName("testTeam2");
    updateTeamCommand.setUserIds(Collections.singletonList(testUser.getId()));
    ConstrainedFields<CreateTeamCommand> fields = fields(CreateTeamCommand.class);

    mvc()
        .perform(
            post("/api/teams/" + teamId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updateTeamCommand)))
        .andExpect(status().isOk())
        .andDo(
            document(
                "teams/update",
                requestFields(
                    fields.withPath("name").description("The name of the team"),
                    fields
                        .withPath("userIds")
                        .description("A list of user IDs to add to the team"))))
        .andReturn();

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam2", teams.get(0).getName());
    Assertions.assertEquals("username2", teams.get(0).getMembers().get(0).getUsername());
    Assertions.assertEquals("username", teams.get(0).getMembers().get(1).getUsername());
  }

  @Test
  void updateTeamWithNoUsersSuccessfully() throws Exception {
    UpdateTeamCommand updateTeamCommand = new UpdateTeamCommand();
    updateTeamCommand.setName("testTeam2");

    mvc()
        .perform(
            post("/api/teams/" + teamId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updateTeamCommand)))
        .andExpect(status().isOk())
        .andReturn();

    List<TeamEntity> teams = teamRepository.findAllWithMembers();
    Assertions.assertEquals(1L, teams.size());
    Assertions.assertEquals("testTeam2", teams.get(0).getName());
  }

  @Test
  void throwsExceptionWhenTeamAlreadyExists() throws Exception {
    UpdateTeamCommand updateTeamCommand = new UpdateTeamCommand();
    updateTeamCommand.setName("testTeam3");

    // create team
    mvc()
        .perform(
            post("/api/teams/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updateTeamCommand)))
        .andExpect(status().isCreated())
        .andReturn();

    // try to update team with the same name
    MvcResult result =
        mvc()
            .perform(
                post("/api/teams/" + teamId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(updateTeamCommand)))
            .andExpect(status().isConflict())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Team with name testTeam3 already exists!", errorMessage);
  }

  @Test
  void throwsExceptionWhenUserNotFound() throws Exception {
    UpdateTeamCommand command = new UpdateTeamCommand();
    command.setName("testTeam");
    command.setUserIds(Collections.singletonList(100L));

    MvcResult result =
        mvc()
            .perform(
                post("/api/teams/" + teamId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("User with id 100 not found.", errorMessage);
  }

  @Test
  void throwsExceptionWhenTeamDoesNotExist() throws Exception {
    UpdateTeamCommand command = new UpdateTeamCommand();
    command.setName("testTeam");

    MvcResult result =
        mvc()
            .perform(
                post("/api/teams/1000")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(command)))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Team with id 1000 not found.", errorMessage);
  }
}
