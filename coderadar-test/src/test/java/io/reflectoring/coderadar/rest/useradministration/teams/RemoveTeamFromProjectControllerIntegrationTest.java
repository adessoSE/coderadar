package io.reflectoring.coderadar.rest.useradministration.teams;

import static io.reflectoring.coderadar.rest.JsonHelper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.TeamRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

class RemoveTeamFromProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private TeamRepository teamRepository;

  @Autowired private ProjectRepository projectRepository;

  private TeamEntity teamEntity;
  private ProjectEntity testProject;

  @BeforeEach
  void setUp() {
    testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsOnline(true);
    testProject.setVcsPassword("testPassword");
    testProject.setVcsUsername("testUser");
    projectRepository.save(testProject);

    teamEntity = new TeamEntity();
    teamEntity.setName("testTeam");
    teamEntity.setProjects(Collections.singletonList(testProject));
    teamRepository.save(teamEntity, 1);
  }

  @Test
  void removeTeamFromProjectSuccessfully() throws Exception {
    mvc()
        .perform(delete("/api/projects/" + testProject.getId() + "/teams/" + teamEntity.getId()))
        .andExpect(status().isOk())
        .andDo(document("teams/remove/project"))
        .andReturn();

    List<TeamEntity> teams = teamRepository.listTeamsByProjectIdWithMembers(testProject.getId());
    Assertions.assertTrue(teams.isEmpty());
  }

  @Test
  void throwsExceptionWhenTeamIsNotAssignedToProject() throws Exception {
    teamEntity.setProjects(Collections.emptyList());
    teamRepository.save(teamEntity, 1);

    MvcResult result =
        mvc()
            .perform(
                delete("/api/projects/" + testProject.getId() + "/teams/" + teamEntity.getId()))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();

    Assertions.assertEquals(
        "Team with id "
            + teamEntity.getId()
            + " is not assigned to project with id "
            + testProject.getId(),
        errorMessage);
  }

  @Test
  void throwsExceptionWhenProjectDoesNotExist() throws Exception {
    MvcResult result =
        mvc()
            .perform(delete("/api/projects/1000/teams/" + teamEntity.getId()))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Project with id 1000 not found.", errorMessage);
  }

  @Test
  void throwsExceptionWhenTeamDoesNotExist() throws Exception {
    MvcResult result =
        mvc()
            .perform(delete("/api/projects/" + testProject.getId() + "/teams/1000"))
            .andExpect(status().isNotFound())
            .andReturn();

    String errorMessage =
        fromJson(result.getResponse().getContentAsString(), ErrorMessageResponse.class)
            .getErrorMessage();
    Assertions.assertEquals("Team with id 1000 not found.", errorMessage);
  }
}
